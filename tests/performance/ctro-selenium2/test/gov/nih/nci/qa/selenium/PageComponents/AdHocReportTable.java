package gov.nih.nci.qa.selenium.PageComponents;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AdHocReportTable {
	@FindBy(how = How.ID, using = "row")
	WebElement tableRow;

	private WebDriver webDriver;

	public AdHocReportTable(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public int getResultCount() {
		WebElement adHocReport = webDriver.findElement(By.id("resultsAdHocReport"));
		List<WebElement> tableDataCells = adHocReport.findElements(By.tagName("td"));
		Iterator<WebElement> it = tableDataCells.iterator();
		while(it.hasNext()){
			String currentText = it.next().getText();
			// Assume the message is correct.
			if (currentText.contains("Nothing found to display.")){
				return 0;
			}
		}
		
		List<WebElement> pageBanners = adHocReport.findElements(By.className("pagebanner"));
		if (pageBanners.size() > 1){
			// this is bad.
			return -1;
		}

		WebElement pageBanner = pageBanners.get(0);
		String pageBannerText = pageBanner.getText();
		
		if (pageBannerText.contains("One item found.")){
			return 1;
		} else {
			
		}
		
		String[] split = pageBannerText.split(" +");
		String number = split[0];
		return Integer.valueOf(number);
	}

}
