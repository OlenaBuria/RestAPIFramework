Feature: Validationg Place API`s

@AddPlace @Regression
Scenario Outline: Verify if Place is being successfully added using AddPlaceAPI
	Given Add Place Payload "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" with "Post" http request
	Then the API call is success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_Id created maps to "<name>" using "getPlaceAPI"

Examples:
	|name     | language |address            |
	|AAhouse  | English  |World Cross Center | 
#	|BBhouse  | Spanish  |Sea Cross Center   |
#	|CChouse  | Spanish  |Land Cross Center  |

@DeletePlace @Regression
Scenario: Verify if Delete Place functionality is working
	Given DeletePlaceAPI Payload
 	When user calls "deletePlaceAPI" with "Post" http request
 	Then the API call is success with status code 200
	And "status" in response body is "OK"