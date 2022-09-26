package guru.springframework.msscbreweryclient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.msscbreweryclient.web.client.BreweryClient;
import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;

@SpringBootTest
public class BreweryClientTest {

	@Autowired
	BreweryClient client;

	@Test
	void getBeerById() {
		BeerDto dto = client.getBeerById(UUID.randomUUID());
		assertNotNull(dto);
	}

	@Test
	void testSaveNewBeer() {
		// given
		BeerDto beerDto = BeerDto.builder().beerName("New Beer").build();

		URI uri = client.saveNewBeer(beerDto);
		assertNotNull(uri);

		System.out.println(uri.toString());
	}

	@Test
	void testUpdateNewBeer() {
		// given
		BeerDto beerDto = BeerDto.builder().beerName("New Beer name").build();
		client.updateBeer(UUID.randomUUID(), beerDto);

	}

	@Test
	void testDeleteNewBeer() {
		client.deleteBeer(UUID.randomUUID());
	}

	@Test
	void getCustomerById() {
		CustomerDto dto = client.getCustomerById(UUID.randomUUID());
		assertNotNull(dto);
	}

	@Test
	void testSaveCustomer() {
		// given
		CustomerDto customerDto = CustomerDto.builder().name("New Customer").build();

		URI uri = client.saveNewCustomer(customerDto);
		assertNotNull(uri);

		System.out.println(uri.toString());
	}

	@Test
	void testUpdateCustomer() {
		// given
		CustomerDto beerDto = CustomerDto.builder().name("New Customer Name").build();
		client.updateCustomer(UUID.randomUUID(), beerDto);

	}

	@Test
	void testDeleteCustomer() {
		client.deleteCustomer(UUID.randomUUID());
	}

}
