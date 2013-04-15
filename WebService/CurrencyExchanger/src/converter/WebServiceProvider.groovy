package converter

class WebServiceProvider 
{
	private URLConnection connection
		
	def createAddress(vendorName, currency)
	{
		vendorName != "" && currency != "" ? 
		  "http://agile.cs.uh.edu/rate?cur=${currency}&vendor=${vendorName}" : ""
	}	
	
	def connectToWeb(vendorName, currency)
	{
		def address = createAddress(vendorName, currency)
		try{
			connection = new URL(address).openConnection()
			connection.connectTimeout = 5000
			connection.readTimeout = 5000
			successOrFailure(connection.responseCode)
		}
		catch( all ) { "exception" }
	}
	
	def successOrFailure(responseCode) {
		responseCode == 200 ? "success" : "failure"
	}
	
	def getRate(vendorName, currency)
	{
		try{
			connectToWeb(vendorName, currency) ==
			  "success" ? connection.content.text.toDouble() : 0.0
		}
		catch( all ) { 0.0 }
	}
}
