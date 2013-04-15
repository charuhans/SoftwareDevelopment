package converter;


import groovy.util.GroovyTestCase;

class WebServiceProviderTest extends GroovyTestCase
{
	WebServiceProvider serviceProvider

	protected void setUp()
	{
		serviceProvider = new WebServiceProvider()
	}
	
	void testCanary()
	{
		assert true
	}

	void testURLValidityWhenVendorAndCurrencyAvailable()
	{
		def address = 
		  "http://agile.cs.uh.edu/rate?cur=GBR&vendor=vendor1"
		  
		assert address == 
		  serviceProvider.createAddress("vendor1", "GBR")
	}
	
	void testURLValidityWhenNoVendor()
	{
		assert "" == serviceProvider.createAddress("", "GBR")
	}
	
	void testURLValidityWhenNoCurrency()
	{
		assert "" == serviceProvider.createAddress("vendor1", "")
	}
	
	void testURLValidityWhenVendorAndCurrencyUnavailable()
	{
		assert "" == serviceProvider.createAddress("", "")
	}
	
	void testResponseSuccess() {
		assert "success" == serviceProvider.successOrFailure(200)  
	}	

	void testResponseFailure() {
		assert "failure" == serviceProvider.successOrFailure(500)  
	}	
		
	void testConnectionWithNoVendor()
	{
		def responseCode = serviceProvider.connectToWeb("", "GBR")
		assert "exception" == responseCode			
	}
	
	void testConnectionWithNoCurrency()
	{
		def responseCode = 
		  serviceProvider.connectToWeb("vendor1", "")
		  
		assert "exception" == responseCode			
	}

	void testConnectionWithNoVendorAndCurrency()
	{
		def responseCode = serviceProvider.connectToWeb("", "")
		assert "exception" == responseCode			
	}
	
	void testGetRateFromURLWhenParametersValid()
	{		  
		assert 0.0 <= serviceProvider.getRate('vendor1', 'GBR')
	}
	
	void testGetRateWhenParametersValidButConnectFailed()
	{
		serviceProvider.metaClass.connectToWeb =
		  { vendorName, currency -> "failure" }
		
		assert 0.0 == serviceProvider.getRate('vendor1', 'GBR')
	}
	
	void testGetRateWhenParametersValidButExceptionThrown()
	{
		serviceProvider.metaClass.connectToWeb =
		  { vendorName, currency -> "exception" }
		
		assert 0.0 == serviceProvider.getRate('vendor1', 'GBR')
	}
	
	void testGetRateFromURLWhenCurrencyMissing()
	{
		assert 0.0 == serviceProvider.getRate('vendor1', "")
	}
	
	void testGetRateFromURLWhenVendorMissing()
	{
		assert 0.0 == serviceProvider.getRate("", 'GBR')
	}
	
	void testGetRateFromURLWhenParametersMissing()
	{
		assert 0.0 == serviceProvider.getRate("", "")
	}
	
	void testConnectionSuccessButContentNotRead()
	{
		serviceProvider.metaClass.connectToWeb =
		  { vendorName, currency -> "success" }
		  
		assert 0.0 == serviceProvider.getRate("vendor1", "GBR")
	}
	
}
