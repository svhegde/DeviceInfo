# DeviceInfo
Android application demonstrates connecting to web server and perform GET and POST requests. 

This application has 3 main screens
  * **Home screen** 
  * **Device Details** 
  * **Add device** 


####Home Screen- Lists all the device and allows user to add/ delete the item
1. *Completed*
   1. Implemented the list and swipe on delete using Recycler View.
   2. Provided the add button at the right hand botton of screen which opens up the add device screen upon clicking.
   3. Have provided optional undo on tool bar which user can check if undo is required on delete.
   4. Implemented the GET operation connecting to the web server and gets all the data using Retrofit.
2. *TODO*
  1.  Alert pop up on delete.

####Device Details- screen which opens upon clicking on each item from list. This screen also allows the user to check in/Check out the device.
1. *Completed*
   1. Recieving the data from Main activity and displaying on screen.
   2. Dynamically changes the button text based on isCheckedOut field.
   3. Alert pop up allows user to enter name upon clicking Check out.
2. *TODO*
  1.  Page update reflecting the new state of device.

####Add Device- screen which opens uopn clicking add button from home screen, allows the user to add a new device with device details.
1. *Completed*
   1. Allows the user to enter all the 3 mentioned fields.
   2. Null/Empty check for all the fields.
   3. Click on save will return to the home page.
2. *TODO*
  1.  Implementing the POST operation to post the data for new device.
