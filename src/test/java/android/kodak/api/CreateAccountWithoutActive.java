package android.kodak.api;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cinatic.DatabaseHelper;
import com.cinatic.LucyApiHelper;
import com.cinatic.StringHelper;
import com.cinatic.constant.TestConstant;
import com.jayway.restassured.response.Response;

public class CreateAccountWithoutActive {
	LucyApiHelper helper;
	DatabaseHelper db = new DatabaseHelper();
	String username = StringHelper.randomNumber("qaexpiredacc", 10).toLowerCase();
	String email = username + TestConstant.emailHost;
	String password = "Aaaa1111";	
	String server = "";
	
	@Parameters({ "server" })
	@BeforeSuite
	public void init(String server) {
		this.server = server;
//		helper = new LucyApiHelper(TestConstant.getKodakUri(server));
		helper = new LucyApiHelper(server);
	}
	
	@Test()
	public void CreateNewUser() {
		Response response = helper.registerUserAccount(email, username, password, password);

		int status = response.path("status");
		String msg = response.path("msg");

		Assert.assertEquals(status, 200, "Error: status is " + status);
		Assert.assertEquals(msg, "Account registration is successful. Please activate your account within 30 days",
				"Error: msg is " + msg);
		
		db.doInsertAccount(username, password, "Kodak_" + this.server);
	}
}
