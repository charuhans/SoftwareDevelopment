package converter;

import java.io.ExpiringCache.Entry;

import groovy.util.GroovyTestCase;

class CurrencyConverterTest extends GroovyTestCase
{
	CurrencyConverter convertor	
	def vendorMapDistinctRates = [
	  vendor1 : 45.5,
	  vendor2 : 55.5, 
	  vendor3 : 100.0 ] 
	
	def vendorMapTwoVendorsSameRates = [
	  vendor1 : 45.5, 
	  vendor2 : 100.0, 
	  vendor3 : 100.0 ]
	
	def vendorMapThreeVendorsSameRates = [
	  vendor1 : 100.0, 
      vendor2 : 100.0, 
	  vendor3 : 100.0 ]
	
	def expectedEntry
	def resultEntry
	
	protected void setUp()
	{		
		convertor = new CurrencyConverter()
	}
	
	void testCanary()
	{
		assert true
	}
	
	void testSelectVendorMaxRate()
	{
		def (expectedName, expectedRate) = [ "vendor3", 100.0 ]
		def (vendorName, maxRate) =
		  convertor.getVendorWithMaxExchangeRate(vendorMapDistinctRates)
			
		assert expectedName == vendorName && expectedRate == maxRate
	}
		
	void testSelectFirstVendorWhenTwoVendorsSameRate()
	{
		def (expectedName, expectedRate) = [ "vendor2", 100.0 ]
	    def (vendorName, maxRate)  = 
		  convertor.
		  getVendorWithMaxExchangeRate(vendorMapTwoVendorsSameRates)
					   
		assert expectedName == vendorName && expectedRate == maxRate
	}
	
	void testSelectFirstVendorWhenThreeVendorsSameRate()
	{
		def (expectedName, expectedRate) = [ "vendor1", 100.0 ]
		def (vendorName, maxRate) = 
		  convertor.
		  getVendorWithMaxExchangeRate(vendorMapThreeVendorsSameRates)
			
		assert expectedName == vendorName && expectedRate == maxRate
	}
	
	void testGetMarkedUpRate()
	{
		assert 98.0 == convertor.getMarkedUpRate(100.0)
	}
	
	void testSelectVendorWhenAllRatesAreBad()
	{
		def vendorMapNoRates = 
		  [ vendor1 : -1.0, vendor2 : -1.0, vendor3 : -1.0 ]
		
		def (expectedName, expectedRate) = [ "", 0.0 ]
		def (vendorName, maxRate)  = 
		  convertor.getVendorWithMaxExchangeRate(vendorMapNoRates)
		
		assert expectedRate == maxRate && expectedName == vendorName
	}
	
	void testSelectVendorWhenSomeRatesAreBad()
	{
		def vendorMapNoRates = 
		  [ vendor1 : -1.0, vendor2 : -1.0, vendor3 : 100.0 ]
		
		def (expectedName, expectedRate) = [ "vendor3", 100.0 ]
		def (vendorName, maxRate)  = 
		  convertor.getVendorWithMaxExchangeRate(vendorMapNoRates)
		
		assert expectedName == vendorName && expectedRate == maxRate
	}
	
	void testSelectVendorWhenNoVendorsAvailable()
	{
		def vendorMapNoRates = [ "" : 100.0, "" : 100.0, "" : 100.0 ]
		def (expectedName, expectedRate) = [ "", 0.0 ]
		def (vendorName, maxRate) = 
		  convertor.getVendorWithMaxExchangeRate(vendorMapNoRates)
		
		assert  expectedRate == maxRate && expectedName == vendorName
	}
	
	void testSelectVendorWhenSomeVendorsUnavailable()
	{
		def vendorMapNoRates = 
		  [ "" : 90, "" : 150.0, vendor3 : 100.0 ]
		
		def (expectedName, expectedRate) = [ "vendor3", 100.0 ]
		
		def (vendorName, maxRate)  = 
		  convertor.getVendorWithMaxExchangeRate(vendorMapNoRates)
		
		assert expectedName == vendorName && expectedRate == maxRate
	}
	
	void testSelectVendorWhenNoVendorsAndRatesAvailable()
	{
		def vendorMapNoRates = [ : ]
		def (vendorName, maxRate) = 
		  convertor.getVendorWithMaxExchangeRate(vendorMapNoRates)
		
		assert  0 == maxRate && "" == vendorName
	}
			
	void testGetExchangeRate()
	{
		convertor = 
		  new CurrencyConverter([ "vendor1", "vendor2", "vendor3" ])
		  
		convertor.metaClass.getVendorRate = 
		  { vendorName, currency -> 100.0 }
		
		def (vendorName, rate) = 
		  convertor.getVendorAndExchangeRate("GBR")
		
		assert "vendor1" == vendorName && 98.0 == rate
	}
	
	void testGetExchangeRateWithNoVendors()
	{
		def isGetVendorRateCalled = false
		convertor = new CurrencyConverter([])
		  
		convertor.metaClass.getVendorRate = 
		  { vendorName, currency -> isGetVendorRateCalled = true }
		  
		def (vendorName, rate) =
		  convertor.getVendorAndExchangeRate("GBR")
		
		assert false == isGetVendorRateCalled
	}
	
	void testGetExchangeRateWithNoCurrency()
	{
		def isGetVendorRateCalled = false
		convertor = 
		  new CurrencyConverter([ "vendor1", "vendor2", "vendor3" ])
		  
		convertor.metaClass.getVendorRate = 
		  { vendorName, currency -> isGetVendorRateCalled = true }
		  
		def (vendorName, rate) =
		  convertor.getVendorAndExchangeRate("")
		
		assert false == isGetVendorRateCalled
	}
	
	void testGetExchangeRateWithNoCurrencyNoVendors()
	{
		def isGetVendorRateCalled = false
		convertor = new CurrencyConverter([])
		 
		convertor.metaClass.getVendorRate = 
		  { vendorName, currency -> isGetVendorRateCalled = true}
		
		def (vendorName, rate) =
		  convertor.getVendorAndExchangeRate("")
		
		assert false == isGetVendorRateCalled
	}

	void testGetExchangeRatefromWeb()
	{	  
		convertor.metaClass.getVendorAndExchangeRate =
		  { currency -> ["vendor1", 98.0] }
		  
		def (vendorName, rate) =
		  convertor.getVendorAndExchangeRate("GBR")
		  
		assert "vendor1" == vendorName && 98.0 == rate
	}
	
	void testGetExchangeRatefromWebWhenNoCurrency()
	{
		def (vendorName, rate) =
		  convertor.getVendorAndExchangeRate("")
		
		assert "" == vendorName  && 0.0 == rate
	}
	
	void testGetVendorRateWhenVendorAndCurrencyAvailable()
	{
		convertor.metaClass.getVendorRate =
		  { vendorName, currency -> 100.0 }
	  
		assert 100.0 == convertor.getVendorRate("v1", "GBR")
	}
	
	void testGetVendorRateWhenVendorUnAvailable()
	{	  
		assert 0.0 == convertor.getVendorRate("", "GBR")
	}
	
	void testGetVendorRateWhenCurrencyUnAvailable()
	{
		assert 0.0 == convertor.getVendorRate("v1", "")
	}
	
	void testGetVendorRateWhenCurrencyandVendorUnAvailable()
	{
		assert 0.0 == convertor.getVendorRate("", "")
	}
	
}
