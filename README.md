# Send-Location-Using-Service
A Project to send the current location to server using service. To configure it for you own use just set a url in the HttpURLs.java class

public static final String URL = "Your Url Here";

Currently implemented as it is a post request which accepts a pyload like the following format.

{"Location":
  {
    "Latitude":23.7557838,
    "Longitude":90.3746569
  },
  "User":
    {
      "Email":"demo@gmail.com",
      "Name":"John Doe"
    }
}
