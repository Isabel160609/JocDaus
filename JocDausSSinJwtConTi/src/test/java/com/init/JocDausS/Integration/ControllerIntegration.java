package com.init.JocDausS.Integration;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.init.JocDausS.ControllerTest;
import com.init.JocDausS.Entity.Jogador;
import com.init.JocDausS.Entity.Tirada;


@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest//(classes= {ControllerIntegration.class})
public class ControllerIntegration {

	@Test
	@Order(1)
	void getJogadorsIntegrationTest() throws JSONException {
	
		String esperado="[\r\n"
				+ "    {\r\n"
				+ "        \"nom\": \"Isabel\",\r\n"
				+ "        \"data\": \"2022-01-13T23:00:00.000+00:00\",\r\n"
				+ "        \"porcentatgeExit\": null,\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"tirades\": [\r\n"
				+ "            {\r\n"
				+ "                \"id\": 1,\r\n"
				+ "                \"valorTirada1\": 2,\r\n"
				+ "                \"valorTirada2\": 3,\r\n"
				+ "                \"guanyar\": false\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"id\": 2,\r\n"
				+ "                \"valorTirada1\": 2,\r\n"
				+ "                \"valorTirada2\": 5,\r\n"
				+ "                \"guanyar\": true\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"nom\": \"Josep\",\r\n"
				+ "        \"data\": \"2022-01-13T23:00:00.000+00:00\",\r\n"
				+ "        \"porcentatgeExit\": null,\r\n"
				+ "        \"id\": 2,\r\n"
				+ "        \"tirades\": [\r\n"
				+ "            {\r\n"
				+ "                \"id\": 3,\r\n"
				+ "                \"valorTirada1\": 2,\r\n"
				+ "                \"valorTirada2\": 5,\r\n"
				+ "                \"guanyar\": true\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"id\": 4,\r\n"
				+ "                \"valorTirada1\": 2,\r\n"
				+ "                \"valorTirada2\": 5,\r\n"
				+ "                \"guanyar\": true\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"id\": 5,\r\n"
				+ "                \"valorTirada1\": 2,\r\n"
				+ "                \"valorTirada2\": 2,\r\n"
				+ "                \"guanyar\": false\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"nom\": \"Anonimo\",\r\n"
				+ "        \"data\": \"2022-01-13T23:00:00.000+00:00\",\r\n"
				+ "        \"porcentatgeExit\": null,\r\n"
				+ "        \"id\": 3,\r\n"
				+ "        \"tirades\": [\r\n"
				+ "            {\r\n"
				+ "                \"id\": 6,\r\n"
				+ "                \"valorTirada1\": 2,\r\n"
				+ "                \"valorTirada2\": 5,\r\n"
				+ "                \"guanyar\": true\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"id\": 7,\r\n"
				+ "                \"valorTirada1\": 2,\r\n"
				+ "                \"valorTirada2\": 5,\r\n"
				+ "                \"guanyar\": true\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"id\": 8,\r\n"
				+ "                \"valorTirada1\": 2,\r\n"
				+ "                \"valorTirada2\": 5,\r\n"
				+ "                \"guanyar\": true\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    }\r\n"
				+ "]";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/players/", String.class);
		System.out.println("el 1 es "+response.getBody());
		
		JSONAssert.assertEquals(esperado,response.getBody(), false);

	}
	
	@Test
	@Order(2)
	void createJogadorIntegrationTest() throws JSONException {
		
		Jogador jogador1=new Jogador(4,"Isa",new java.util.Date(7/1/2022));
		
		String esperada="{\"nom\":\"Isa\",\"data\":\"1970-01-01T00:00:00.000+00:00\",\"porcentatgeExit\":null,\"id\":4,\"tirades\":null}";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Jogador> request = new HttpEntity<Jogador>(jogador1, headers);
		
			
		ResponseEntity<String> response=restTemplate.postForEntity("http://localhost:8080/players", request, String.class);
		
		System.out.println("el 2 es "+response.getBody());
		
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		JSONAssert.assertEquals(esperada,response.getBody(), false);
	}
	
	@Test
	@Order(3)
	void modificaNomJogadorIntegrationTest() throws JSONException {
		
		Jogador jogador1=new Jogador(4,"Ana",new java.util.Date(7/1/2022));
		String nomJogador="Ana";
		
		String esperada=" {\r\n"
				+ "        \"nom\": \"Ana\",\r\n"
				+ "        \"data\": \"1970-01-01T00:00:00.000+00:00\",\r\n"
				+ "        \"porcentatgeExit\": null,\r\n"
				+ "        \"id\": 4,\r\n"
				+ "        \"tirades\": []\r\n"
				+ "    }";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(nomJogador, headers);
			
		ResponseEntity<String> response=restTemplate.exchange("http://localhost:8080/players/4", HttpMethod.PUT,request, String.class);
		
		System.out.println("el 3 es "+response.getBody());
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		JSONAssert.assertEquals(esperada,response.getBody(), false);
	}
	
	@Test
	@Order(4)
	void tirarDausIntegrationTest() throws JSONException {
		
		Jogador jogador1=new Jogador(4,"Ana",new java.util.Date(7/1/2022));
		
		Tirada tirada=new Tirada(9,2,4,false,jogador1);
		
//		String esperada="{\r\n"
//				+ "    \"nom\": \"Ana\",\r\n"
//				+ "    \"data\": \"1970-01-01T00:00:00.000+00:00\",\r\n"
//				+ "    \"porcentatgeExit\": null,\r\n"
//				+ "    \"id\": 4,\r\n"
//				+ "    \"tirades\": [\r\n"
//				+ "        {\r\n"
//				+ "            \"id\": 9,\r\n"
//				+ "            \"valorTirada1\": 2,\r\n"
//				+ "            \"valorTirada2\": 4,\r\n"
//				+ "            \"guanyar\": false\r\n"
//				+ "        }\r\n"
//				+ "    ]\r\n"
//				+ "}";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Tirada> request = new HttpEntity<Tirada>(tirada, headers);
		
		ResponseEntity<String> response=restTemplate.postForEntity("http://localhost:8080/players/4/games/",request, String.class);
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		System.out.println("la 4 es "+response.getBody().toString());
		//JSONAssert.assertEquals(esperada,response.getBody(), false);
	}
	
	@Test
	@Order(5)
	void borrarTiradasIntegrationTest() throws JSONException {
		
		Jogador jogador1=new Jogador(1,"Isabel",new java.util.Date(13/1/2022));
		
		
		String esperada=" {\r\n"
				+ "        \"nom\": \"Isabel\",\r\n"
				+ "        \"data\": \"2022-01-13T23:00:00.000+00:00\",\r\n"
				+ "        \"porcentatgeExit\": null,\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"tirades\": []\r\n"
				+ "    }";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Jogador> request = new HttpEntity<Jogador>(jogador1, headers);
			
		ResponseEntity<String> response=restTemplate.exchange("http://localhost:8080/players/1/games", HttpMethod.DELETE,request, String.class);
		
		System.out.println("la 5 es "+response.getBody());
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		JSONAssert.assertEquals(esperada,response.getBody(), false);
	}
	
	@Test
	@Order(6)
	void llistatPercentatgesIntegrationTest() throws JSONException {
	
		String esperado="{\r\n"
				+ "    \"Isabel\": null,\r\n"
				+ "    \"Ana\": 0.0,\r\n"
				+ "    \"Anonimo\": 100.0,\r\n"
				+ "    \"Josep\": 66.0\r\n"
				+ "}";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/players/porcentatge", String.class);
		System.out.println("el 6 es "+response.getBody());
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		JSONAssert.assertEquals(esperado,response.getBody(), false);

	}
	
	@Test
	@Order(7)
	void llistarTiradesIntegrationTest() throws JSONException {
	
		String esperado="[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 3,\r\n"
				+ "        \"valorTirada1\": 2,\r\n"
				+ "        \"valorTirada2\": 5,\r\n"
				+ "        \"guanyar\": true\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 4,\r\n"
				+ "        \"valorTirada1\": 2,\r\n"
				+ "        \"valorTirada2\": 5,\r\n"
				+ "        \"guanyar\": true\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 5,\r\n"
				+ "        \"valorTirada1\": 2,\r\n"
				+ "        \"valorTirada2\": 2,\r\n"
				+ "        \"guanyar\": false\r\n"
				+ "    }\r\n"
				+ "]";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/players/2/games", String.class);
		System.out.println("el 7 es "+response.getBody());
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		JSONAssert.assertEquals(esperado,response.getBody(), false);

	}
	
	@Test
	@Order(8)
	void rankingMigIntegrationTest() throws JSONException {
	
		String esperado="41.5";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/players/ranking", String.class);
		System.out.println("el 8 es "+response.getBody());
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		JSONAssert.assertEquals(esperado,response.getBody(), false);

	}
	
	@Test
	@Order(9)
	void rankingloserIntegrationTest() throws JSONException {
	
		String esperado="{\r\n"
				+ "    \"nom\": \"Ana\",\r\n"
				+ "    \"data\": \"1970-01-01T00:00:00.000+00:00\",\r\n"
				+ "    \"porcentatgeExit\": null,\r\n"
				+ "    \"id\": 4,\r\n"
				+ "    \"tirades\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 9,\r\n"
				+ "            \"valorTirada1\": 6,\r\n"
				+ "            \"valorTirada2\": 6,\r\n"
				+ "            \"guanyar\": false\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/players/ranking/loser", String.class);
		System.out.println("el 9 es "+response.getBody());
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		//JSONAssert.assertEquals(esperado,response.getBody(), false);

	}
	
	@Test
	@Order(10)
	void rankingWinnerIntegrationTest() throws JSONException {
	
		String esperado="{\r\n"
				+ "    \"nom\": \"Anonimo\",\r\n"
				+ "    \"data\": \"2022-01-13T23:00:00.000+00:00\",\r\n"
				+ "    \"porcentatgeExit\": null,\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"tirades\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 6,\r\n"
				+ "            \"valorTirada1\": 2,\r\n"
				+ "            \"valorTirada2\": 5,\r\n"
				+ "            \"guanyar\": true\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 7,\r\n"
				+ "            \"valorTirada1\": 2,\r\n"
				+ "            \"valorTirada2\": 5,\r\n"
				+ "            \"guanyar\": true\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 8,\r\n"
				+ "            \"valorTirada1\": 2,\r\n"
				+ "            \"valorTirada2\": 5,\r\n"
				+ "            \"guanyar\": true\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/players/ranking/winner", String.class);
		System.out.println("el 10 es "+response.getBody());
		
		assertEquals(HttpStatus.OK,response.getStatusCode());
		JSONAssert.assertEquals(esperado,response.getBody(), false);

	}
}
