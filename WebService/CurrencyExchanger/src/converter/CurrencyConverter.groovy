package converter

import java.security.Principal;

class CurrencyConverter 
{
	private List vendorNames
		
	CurrencyConverter(_vendorNames)
	{
		vendorNames = _vendorNames
	}
	
	def getVendorWithMaxExchangeRate(vendorNameAndRateMap)
	{			
		def vendorWithMaxRate = 
		  vendorNameAndRateMap.findAll{ it.value > 0 && it.key != "" }
		  .max{ it.value }
		  	
		vendorWithMaxRate ? 
		  [ vendorWithMaxRate.key, vendorWithMaxRate.value ] : [ "", 0.0 ]		
	 }

	def getMarkedUpRate(rate)
	{
		rate *  0.98
	}	
	
	def getVendorAndExchangeRate(currency)
    {		
        def vendorAndRateMap = 
          currency !=  "" ? vendorNames.collectEntries{
		    [ it, getVendorRate(it, currency) ] } : [:]

        def (vendorName, rate) = 
          getVendorWithMaxExchangeRate(vendorAndRateMap)
	
        [vendorName, getMarkedUpRate(rate)]		
    }
	
	def getVendorRate(vendorName, currency)
	{
		WebServiceProvider webServiceProvider =
		  new WebServiceProvider()
		  
		webServiceProvider.getRate(vendorName, currency)
	}
}
