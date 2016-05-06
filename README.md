# DeviceInfo
Android application demonstrates connecting to web server and perform GET and POST requests. Implements  "swipe to delete" on a recycler view with no 3rd party Library. It also includes REST API implementation using Retrofit .

This application has 3 main screens
  * **Home screen** 
  * **Device Details** 
  * **Add device** 


####Home Screen- Lists all the device and allows user to add/ delete the item
1. *Completed*
   1. Implemented the list and swipe on delete using Recycler View.
   2. Provided the add button at the right hand bottom of screen which opens up the add device screen upon clicking.
   3. Have provided optional undo on tool bar which user can check if undo is required on delete.
   4. Implemented the GET operation connecting to the web server and gets all the data using Retrofit.
   5. On clicking on each device a device details screen opens up showing all the details of the device.

####Device Details- screen which opens upon clicking on each item from list. This screen also allows the user to check in/Check out the device.
1. *Completed*
   1. Receiving the data from Main activity and displaying on screen.
   2. Dynamically changes the button text based on isCheckedOut field.
   3. Alert pop up allows user to enter name upon clicking Check out.
   4. Page refresh showing new state of device.


####Add Device- screen which opens uopn clicking add button from home screen, allows the user to add a new device with device details.
1. *Completed*
   1. Allows the user to enter all the 3 mentioned fields.
   2. Null/Empty check for all the fields.
   3. Click on save will return to the home page.
   4. Implemented back button press, upon clicking back button a confirm action pop up opens up.

####Explanation for some of the technical decisions made while developing the application.
1. Using Recyclerview -- It gives swipe on delete functionality without using 3rd party library.I prefer not to use those libs unless I absolutely have to. Sometimes Libraries might be doing a lot more than you need it for and with that comes a burden on your precious method limit count, possibly longer builds.
2. Using Retrofit -- It is simple and easy to make API calls.
3. Adding Undo functionality -- Unlike alert pop up on swipe to delete, undo option gives better user experience. Also, it is similar to how gmail works.
4. Making return type for @POST("/devices/{id}") as Void --  Currently POST api for update is not returning anything in response body due to which it cannot parse the JSON and throws an error.
5. SQLite database -- Have opted SQLite databse as data storage mechanism because it is persistant, higher performance data retrieval is much more robust.


