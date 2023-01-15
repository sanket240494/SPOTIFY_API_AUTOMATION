package com.bridgelabz.qa.restassured;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Restassured_Api_Automation_Test {
	
	String token;
	String userId;
	String newPlaylist1;

	@BeforeTest
	public void getToken() {
		token = "Bearer BQDNYrq1rcZLVtTVD7cxfvmSha6kigVeAihCrB-HCjCU64jzlVXBdRselA_v-9b0ifi4TUpZxcb95AjSiC5fsnuBvWLX7-TDdxRKXz8aaLsFEHtJliqwE2-CdDg3Z0lSYtfHztahPhcGSIYfDCgSmdOIYMRYqWC4efptlVZnhaDi6WR7pUQ0R9f_KyHbS2aJXNBOWtiQlYrEKf4AI0Oyr9qz";
		
	}
	
	//*********************** User Profile ***********************//
	
	@Test(priority = 1)
	public void get_Current_user_Profile() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/me");
				response.prettyPrint();
				userId = response.path("id");
				System.out.println("Current User Id is :"+userId);
				response.then().assertThat().statusCode(200);
				Assert.assertEquals(userId, "31uaam2dl7vmcfc7r6srv3amsbum");
	}
	@Test(priority = 2)
	public void get_Users_Profile() {;
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/users/"+userId);
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	
	
	//***************************** Search *******************************//
	
	@Test(priority = 3)
	public void search_for_Item() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
//				.queryParams("q", "latamangeshkar")
//				.queryParams("type", "track")
				.pathParam("q","latamangeshkar")
				.pathParam("type", "track")
				.when()
//				.get("https://api.spotify.com/v1/search");
				.get("https://api.spotify.com/v1/search?q={q}&type={type}");
				 response.prettyPrint();
				 response.then().assertThat().statusCode(200);
	}
	
	//************************ PlayList ********************************//
	
	@Test(priority = 4)
	public void create_Playlist() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.body("{\r\n"
						+ "  \"name\": \"New Playlist-3\",\r\n"
						+ "  \"description\": \"Bollywood Songs-3\",\r\n"
						+ "  \"public\": false\r\n"
						+ "}")
				.when()
				
				.post("https://api.spotify.com/v1/users/"+userId+"/playlists");
				System.out.println("Current User Id is :"+userId);
				response.prettyPrint();
				newPlaylist1 = response.path("id");
				System.out.println("Current Playlist id is :"+newPlaylist1);
				response.then().assertThat().statusCode(201);
				
	}
	@Test(priority = 5)
	public void get_Current_User_Playlists() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("limit", 10)
				.queryParams("offset", 5)
				.when()
				.get("https://api.spotify.com/v1/me/playlists");
				 response.prettyPrint();
				 response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 6)
	public void get_Playlist_Cover_Image() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+newPlaylist1+"/images");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 7)
	public void get_PlayList_Item() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+newPlaylist1+"/tracks");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	
	@Test (priority = 8)
	public void get_Playlist() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+newPlaylist1);
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 9)
	public void get_User_Playlists() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("limit", 10)
				.queryParams("offset", 5)
				.when()
				.get("https://api.spotify.com/v1/users/"+userId+"/playlists");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 10)
	public void add_Items_to_Playlist() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("uris","spotify:track:4rNlSH6WP8ML0Yke8sPmNx,spotify:track:10qfuMs3MlQClbjc2aV0ez,")
				.when()
				.post("https://api.spotify.com/v1/playlists/"+newPlaylist1+"/tracks");
				response.prettyPrint();
				response.then().assertThat().statusCode(201);
	}
	
	@Test (priority = 11)
	public void update_Playlist_Items() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("uris","spotify:track:4zgXG7wuOSmUq9YElsfD6z")
				.when()
				.put("https://api.spotify.com/v1/playlists/"+newPlaylist1+"/tracks");
				response.prettyPrint();
				response.then().assertThat().statusCode(201);
	}
	
	@Test (priority = 12)
	public void change_Playlist_Details() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.body("{\r\n"
						+ "  \"name\": \"CQA-112-Api-Spotify-Auto\",\r\n"
						+ "  \"description\": \"All Songs\",\r\n"
						+ "  \"public\": false\r\n"
						+ "}")
				.when()
				.put("https://api.spotify.com/v1/playlists/"+newPlaylist1);
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 13)
	public void remove_Playlist_Items() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("uris","spotify:track:4zgXG7wuOSmUq9YElsfD6z")
				.when()
				.delete("https://api.spotify.com/v1/playlists/"+newPlaylist1+"/tracks");
				response.prettyPrint();
				response.then().assertThat().statusCode(204);		
	}
	
	
	//****************************** Track **************************************//
	
	@Test (priority = 14)
	public void get_Track_Audio_Analysis() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/audio-analysis/2NTVtNcpC0i1R7LVGaCZCF");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 15)
	public void get_Tracks_Audio_Features() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("ids","2NTVtNcpC0i1R7LVGaCZCF,16xg7UwH9hbs96vCD8k6bC")
				.when()
				.get("https://api.spotify.com/v1/audio-features");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 16)
	public void get_Track_Audio_Features() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("id","1iZLpuGMr4tn1F5bZu32Kb")
				.when()
				.get("https://api.spotify.com/v1/audio-features/1iZLpuGMr4tn1F5bZu32Kb");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 17)
	public void get_Several_Tracks() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("ids","2NTVtNcpC0i1R7LVGaCZCF,16xg7UwH9hbs96vCD8k6bC,1iZLpuGMr4tn1F5bZu32Kb")
				.when()
				.get("https://api.spotify.com/v1/tracks");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 18)
	public void get_Track() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("id","1iZLpuGMr4tn1F5bZu32Kb")
				.when()
				.get("https://api.spotify.com/v1/tracks/1iZLpuGMr4tn1F5bZu32Kb");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	//****************************** Player **********************************//

	
	@Test (priority = 19)
	public void get_Users_Queue() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/me/player/queue");
				response.prettyPrint();
				
	}
	
	
	@Test (priority = 20)
	public void get_Recently_Played_Tracks() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("limit",10)
				.when()
				.get("https://api.spotify.com/v1/me/player/recently-played");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	
	@Test (priority = 21)
	public void get_Get_Playback_State() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("market","ES")
				.when()
				.get("https://api.spotify.com/v1/me/player");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
	}
	
	
	@Test (priority = 22)
	public void get_Available_Devices() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/me/player/devices");
				response.prettyPrint();
				response.then().assertThat().statusCode(200);
				
	}
	
	@Test (priority = 22)
	public void get_Currently_Playing_Track() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/me/player/currently-playing");
				response.prettyPrint();
				
	}
	
	
	@Test (priority = 23)
	public void skip_To_Next() {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization",token)
				.queryParams("device_id","a49a90dda477e49370dd8a93b1ea32cd0de3911b")
				.when()
				.post("https://api.spotify.com/v1/me/player/next");
				response.prettyPrint();
				response.then().assertThat().statusCode(201);
				//Premium Required//
	}
	
	
	
}
