package com.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.main.PG4Case;

public class DC extends PageUtils {

	private long startTime;
	private long endTime;
	public void startDC(int parents,int children) throws InterruptedException {
		System.out.println("----------------Starting DC");
		initiateDC();
		caseActionConfirm();
		householdInfo();
		householdAddrSummary();
		indvDetailsDriver(parents+children);
		headOfHousehold();
		programReqSummary();
		relationshipDetailsPG5(children);
		livingArrangementDetails();
		individualDemographicsDetails();
		individualDemographicsSSN();
		individualDemographicsChildren(children);
		childCareHouseholdAsset();
		immunizationDetailsChildren(children);
		childCareReasonForCare();
		householdIndividualQuestion();
		disabilityDetails(children);
		complianceQuestion();
		nonComplianceDetails(children);
		incomeQuestion();
		unearnedIncomeDetails(parents);
		earnedIncomeDetails(parents);
		earnedIncomeWorksheet();
		selfEmploymentDetailsBusinessRecord(parents);
		selfEmployementBusinessRecord();
		expenseQuestions();
		activitySchedule(parents);
		fastLinkUpdate();
		wrapUp();
		eligibilityDet();
		delayReasons();
		eligibilityAuth();
		System.out.println("----------------Finished DC");
	}

	public void initiateDC() throws InterruptedException {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"caseAction\"]")));
		dropdown.selectByIndex(1);
		Thread.sleep(500);
		PG4Case.driver.findElement(By.xpath("//*[@id=\"MainHeader\"]/table[5]/tbody/tr[2]/td/div/input"))
				.click();
	}

	public void caseActionConfirm() {
		waitPageLoad();
		Select dropdown = new Select(
				PG4Case.driver.findElement(By.xpath("//*[@id=\"caseModeConfirmation\"]")));
		dropdown.selectByIndex(2);
		PG4Case.driver.findElement(By.xpath("//*[@name=\"submit\"]")).click();
	}

	public void householdInfo() {
		waitPageLoad();
		PG4Case.CASENUM = PG4Case.driver.findElement(By.xpath("//*[@id=\"genericHeader\"]/div[3]/p/a")).getText();
		//System.out.println(rightPadding("Case Number")+" "+PG4Case.CASENUM);
		PG4Case.driver.findElement(By.xpath("//*[@id=\"MainHeader\"]/table[4]/tbody/tr/td/div[2]/input"))
				.click();
	}

	public void householdAddrSummary() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}

	public void indvDetails() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"verifications\"]")));

		dropdown.selectByValue("HC");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck3\"]")));
		dropdown.selectByValue("HC");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"primaryRace\"]")));
		dropdown.selectByIndex(1);
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"ethnicity\"]")));
		dropdown.selectByIndex(1);

		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}

	public void indvHHStatus() {
		waitPageLoad();
		Select dropdown = new Select(
		PG4Case.driver.findElement(By.xpath("//*[@id=\"householdVerification\"]")));
		dropdown.selectByValue("HC");

		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();

	}
	
	public void indvDetailsDriver(int indvNum) {
		waitPageLoad();
		for (int i = 0; i < indvNum; i++) {
			indvDetails();
			indvHHStatus();
		}
	}

	public void indvDetailsChildren(int children) {
		waitPageLoad();
		for (int i = 0; i < children; i++) {
			indvDetails();
			indvHHStatus();
		}
	}

	public void headOfHousehold() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}

	public void programReqSummary() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}

	public void relationshipDetailsPG5(int children) throws InterruptedException {
		startTime = System.currentTimeMillis();
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rowsBox\"]")));
		JavascriptExecutor jse;
		WebElement wb = PG4Case.driver.findElement(By.xpath("//*[@id=\"rowsBox\"]"));
		boolean parentsFlag = false;
		boolean childrenFlag = false;
		int length = 0;
		jse = (JavascriptExecutor) PG4Case.driver;
		Select select;
		String rel;
		String name1;
		String name2;
		int records = ((Long) jse.executeScript("return $('#grid0').jqGrid('getGridParam').records;", wb)).intValue();
		for (int i = 1; i <= records; i++) {
			name1 = PG4Case.driver.findElement(By.xpath("//*[@id=\""+i+"\"]/td[2]")).getText();
			rel = PG4Case.driver.findElement(By.xpath("//*[@id=\""+i+"\"]/td[3]")).getText();
			name2 = PG4Case.driver.findElement(By.xpath("//*[@id=\""+i+"\"]/td[4]")).getText();
			if("".equals(rel) || "Ward of".equals(rel)) {
				PG4Case.driver.findElement(By.xpath("//*[@id=\"" + i + "\"]/td[9]/img")).click();
			}else {
				continue;
			}
			
			waitPageLoad();
			wb = PG4Case.driver.findElement(By.xpath("//*[@id=\"cin_array\"]"));
			parentsFlag = AR.parentsNameList.contains(name1);
			childrenFlag = AR.childrenNameList.contains(name2);
			if(parentsFlag && childrenFlag) {
				 select = new Select(wb);
				 select.deselectAll();
				 for(int a = 0; a < AR.childrenIdList.size(); a++) {
						select.selectByValue(String.valueOf(AR.childrenIdList.get(a)));
				 }
				 dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"relationshipType\"]")));
				 dropdown.selectByValue("PA");
				 dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"relationshipVerification\"]")));
				 dropdown.selectByValue("HC");
			}else if (!parentsFlag && !childrenFlag){
				select = new Select(wb);
				select.deselectAll();
				 for(int a = 0; a < AR.parentsIdList.size(); a++) {
						select.selectByValue(String.valueOf(AR.parentsIdList.get(a)));
				}
				dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"relationshipType\"]")));
				dropdown.selectByValue("AC");
				dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"relationshipVerification\"]")));
				dropdown.selectByValue("HC");
				 
				inputValue(PG4Case.MONTH, "//*[@id=\"monthdateOfAdoption\"]");
				inputValue(PG4Case.DAY, "//*[@id=\"datedateOfAdoption\"]");
				inputValue(String.valueOf(Integer.parseInt(PG4Case.YEAR)-1), "//*[@id=\"yeardateOfAdoption\"]");
			}else if (parentsFlag && !childrenFlag){
				dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"relationshipType\"]")));
				dropdown.selectByValue("SPS");
				dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"relationshipVerification\"]")));
				dropdown.selectByValue("HC");
				
			}else {
				select = new Select(wb);
				select.deselectAll();
				for (int a = 0; a < AR.childrenIdList.size(); a++) {
					if(name1.equals(String.valueOf(AR.childrenNameList.get(a)))) {
						continue;
					}else {
						select.selectByValue(String.valueOf(AR.childrenIdList.get(a)));
					}
				}
				dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"relationshipType\"]")));
				dropdown.selectByValue("SB");
				dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"relationshipVerification\"]")));
				dropdown.selectByValue("HC");
			}			
			PG4Case.driver.findElement(By.xpath("//*[@id=\"Add\"]")).click();
			wb = PG4Case.driver.findElement(By.xpath("//*[@id=\"rowsBox\"]"));
			jse.executeScript("$('#grid0').setGridParam({rowNum:"+records+"});", wb);
			jse.executeScript("$('#grid0').trigger('reloadGrid');", wb);
		}
		Thread.sleep(1000);
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
		endTime = System.currentTimeMillis();
		System.out.println("\t\t\tTime taken: " + (endTime - startTime)/1000 + " seconds");
	}

	public void livingArrangementDetails() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void individualDemographicsDetails() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck42\"]")));
		dropdown.selectByValue("Y");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"residentVerification\"]")));
		dropdown.selectByValue("HC");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	public void individualDemographicsDetailsChild() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck42\"]")));
		dropdown.selectByValue("Y");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"residentVerification\"]")));
		dropdown.selectByValue("HC");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck100\"]")));
		dropdown.selectByValue("US");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"citizenshipDocumentType\"]")));
		dropdown.selectByValue("BRT");
		
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
		
	}
	
	public void individualDemographicsSSN() throws InterruptedException {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
		Thread.sleep(500);
	}
	
	public void individualDemographicsChildren(int children) throws InterruptedException {
		waitPageLoad();
		for(int i = 0; i < children; i ++) {
			waitPageLoad();
			individualDemographicsDetailsChild();
			individualDemographicsSSN();
			
		}
	}
	
	public void childCareHouseholdAsset() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"householdAssets\"]")));
		dropdown.selectByValue("N");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"verification\"]")));
		dropdown.selectByValue("CS");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void immunizationDetailsChildren(int children) {
		waitPageLoad();
		Select dropdown;
		for(int i = 0; i < children; i ++) {
			waitPageLoad();
			dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"meetsImmunizationRequirements\"]")));
			dropdown.selectByValue("Y");
			dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"verification\"]")));
			dropdown.selectByValue("HC");
			PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();	
		}
	}
	
	public void childCareReasonForCare() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"reasonForCare\"]")));
		dropdown.selectByValue("EM");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();	
	}
	
	public void householdIndividualQuestion() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck2\"]")));
		dropdown.selectByValue("Y");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void disabilityDetails(int children) {
		waitPageLoad();
		int randomNum = ThreadLocalRandom.current().nextInt(0, children);
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"cin\"]")));
		dropdown.selectByValue(String.valueOf(AR.childrenIdList.get(randomNum)));
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck1\"]")));
		dropdown.selectByValue("SN");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck2\"]")));
		dropdown.selectByValue("HC");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void complianceQuestion() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck5\"]")));
		dropdown.selectByValue("Y");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void nonComplianceDetails(int children) {
		waitPageLoad();
		int randomNum = ThreadLocalRandom.current().nextInt(0, children);
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"cin\"]")));
		dropdown.selectByValue(String.valueOf(AR.childrenIdList.get(randomNum)));
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"nonCoOpType\"]")));
		dropdown.selectByValue("AC");
		inputValue(PG4Case.MONTH, "//*[@id=\"monthnonCoOpBeginDt\"]");
		inputValue(PG4Case.DAY, "//*[@id=\"datenonCoOpBeginDt\"]");
		inputValue(PG4Case.YEAR, "//*[@id=\"yearnonCoOpBeginDt\"]");
		
		inputValue(PG4Case.MONTH, "//*[@id=\"monthdateIndividualComplied\"]");
		inputValue(PG4Case.DAY, "//*[@id=\"datedateIndividualComplied\"]");
		inputValue(PG4Case.YEAR, "//*[@id=\"yeardateIndividualComplied\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void incomeQuestion() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck4\"]")));
		dropdown.selectByValue("Y");
		dropdown = new Select(PG4Case.driver.findElement(By.name("isCurrentlyEmployed")));
		dropdown.selectByValue("Y");
		
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck2\"]")));
		dropdown.selectByValue("Y");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void unearnedIncomeDetails(int parents) {
		waitPageLoad();
		int randomNum = ThreadLocalRandom.current().nextInt(0, parents);
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"cin\"]")));
		dropdown.selectByValue(String.valueOf(AR.parentsIdList.get(randomNum)));
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"unearnedIncomeTp\"]")));
		dropdown.selectByValue("PE");
		inputValue("Minion's Harbor", "//*[@id=\"incomeSource\"]");
		inputValue("314", "//*[@id=\"amount\"]");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"verification\"]")));
		dropdown.selectByValue("HC");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void earnedIncomeDetails(int parents) {
		waitPageLoad();
		int randomNum = ThreadLocalRandom.current().nextInt(0, parents);
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"cin\"]")));
		dropdown.selectByValue(String.valueOf(AR.parentsIdList.get(randomNum)));
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"type\"]")));
		dropdown.selectByValue("WA");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"payFrequency\"]")));
		dropdown.selectByValue("MO");
		inputValue("Minion's Bar", "//*[@id=\"employerName\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"Foreign\"]")).click();
		inputValue("The End", "//*[@id=\"addressLine1Foreign\"]");
		inputValue("of The Rainbow", "//*[@id=\"addressLine2Foreign\"]");
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"countryResidenceForeign\"]")));
		dropdown.selectByValue("VN");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void earnedIncomeWorksheet() {
		waitPageLoad();
		String[] firstDate = PG4Case.driver.findElement(By.xpath("//*[@id=\"worksheetfirstdate\"]")).getText().split("/");
		inputValue(firstDate[0], "//*[@id=\"monthfirstPayDateOnAfter\"]");
		inputValue(firstDate[1], "//*[@id=\"datefirstPayDateOnAfter\"]");
		inputValue(firstDate[2], "//*[@id=\"yearfirstPayDateOnAfter\"]");
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"paidOnLastDay\"]")));
		dropdown.selectByValue("N");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"buildWs\"]")).click();
		waitPageLoad();
		JavascriptExecutor jse = (JavascriptExecutor) PG4Case.driver;
		WebElement wb = PG4Case.driver.findElement(By.xpath("//*[@id=\"gbox_grid0\"]"));
		int records = ((Long) jse.executeScript("return $('#grid0').jqGrid('getGridParam').records;", wb)).intValue();
		for(int i = 0; i < records; i++) {
			PG4Case.driver.findElement(By.xpath("//*[@id=\""+(i+1)+"\"]/td[3]")).click();
			inputValue("1500", "//*[@id=\""+(i+1)+"_wsAmount\"]");
			dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\""+(i+1)+"_actualAnticipated\"]")));
			dropdown.selectByValue("AC");
			inputValue("40", "//*[@id=\""+(i+1)+"_numberOfHoursWorked\"]");
			dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\""+(i+1)+"_verification\"]")));
			dropdown.selectByValue("HC");
		}
		PG4Case.driver.findElement(By.xpath("//*[@id=\"pager0\"]")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"pager0\"]")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void selfEmploymentDetailsBusinessRecord(int parents) {
		waitPageLoad();
		int randomNum = ThreadLocalRandom.current().nextInt(0, parents);
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"cin\"]")));
		dropdown.selectByValue(String.valueOf(AR.parentsIdList.get(randomNum)));
		
		inputValue(generateString(), "//*[@id=\"businessName\"]");
		
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck5\"]")));
		JavascriptExecutor jse = (JavascriptExecutor) PG4Case.driver;
		WebElement wb = PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck5\"]"));
		int options = ((Long) jse.executeScript("return document.getElementById('rtcheck5').options.length;", wb)).intValue();
		randomNum = ThreadLocalRandom.current().nextInt(1, options);
		dropdown.selectByIndex(randomNum);
		
		
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck7\"]")));
		dropdown.selectByIndex(1);
		
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck8\"]")));
		dropdown.selectByIndex(1);
		
		randomNum = ThreadLocalRandom.current().nextInt(1, 100);
		inputValue(String.valueOf(randomNum), "//*[@id=\"rtcheck6\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void selfEmployementBusinessRecord() throws InterruptedException {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"selfEmpIncomeExpensesSw\"]")));
		dropdown.selectByIndex(1);
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"numberOfMonthsRepresented\"]")));
		int randomNum1 = ThreadLocalRandom.current().nextInt(1, 12);
		dropdown.selectByIndex(randomNum1);
		
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"numberOfMonthsAnnually\"]")));
		int randomNum2 = ThreadLocalRandom.current().nextInt(Math.abs(12-randomNum1), 13);
		dropdown.selectByIndex(randomNum2);
		
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"monthsIntendedToCover\"]")));
		int randomNum3 = ThreadLocalRandom.current().nextInt(randomNum1, 12);
		dropdown.selectByIndex(randomNum3);
		Thread.sleep(800);
		PG4Case.driver.findElement(By.xpath("//*[@id=\"rowAdd\"]/div")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"rowAdd\"]/div")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"rowAdd\"]/div")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"rowAdd\"]/div")).click();
		
		inputValue("500", "//*[@id=\"new_row_amount\"]");
		inputValue(PG4Case.MONTH+"/"+PG4Case.DAY+"/"+PG4Case.YEAR, "//*[@id=\"new_row_monthandYear\"]");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"form2\"]/span/h2")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"form2\"]/span/h2")).click();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void expenseQuestions() {
		waitPageLoad();
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"rtcheck3\"]")));
		dropdown.selectByIndex(1);
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void activitySchedule(int parents) {
		waitPageLoad();
		int randomNum = ThreadLocalRandom.current().nextInt(0, parents);
		Select dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"cin\"]")));
		dropdown.selectByValue(String.valueOf(AR.parentsIdList.get(randomNum)));

		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"activityType\"]")));
		dropdown.selectByIndex(1);
		
		dropdown = new Select(PG4Case.driver.findElement(By.xpath("//*[@id=\"verification\"]")));
		dropdown.selectByValue("HC");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void fastLinkUpdate() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"Next\"]")).click();
	}
	
	public void wrapUp() {
		waitPageLoad();
		PG4Case.CASENUM = PG4Case.driver.findElement(By.xpath("//*[@id=\"caseNumber\"]")).getAttribute("value");
		PG4Case.driver.findElement(By.xpath("//*[@id=\"SUBMIT\"]")).click();
	}
	
	public void eligibilityDet() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"next\"]")).click();
	}
	
	public void delayReasons() {
		waitPageLoad();
		PG4Case.driver.findElement(By.xpath("//*[@id=\"actionButtonNext\"]")).click();
	}
	
	public void eligibilityAuth() {
		waitPageLoad();
		JavascriptExecutor jse = (JavascriptExecutor) PG4Case.driver;
		jse.executeScript("document.getElementById('selectAll').checked = true;"
				+ "selectAllCheckBoxEdbc(document.form1);"
				+ "checkedFipEdgOnSelectAll();");

		PG4Case.driver.findElement(By.xpath("//*[@id=\"authorizeButton\"]")).click();
	}
}
