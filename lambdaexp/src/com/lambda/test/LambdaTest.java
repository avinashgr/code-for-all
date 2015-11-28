package com.lambda.test;


/**
 * Created to test out the lambda expression support in java 8
 * @author aranjalkar
 *
 */
public class LambdaTest {
	//wihtout lambda
/*	   String firstname;
	   String lastname;
	   public LambdaTest() {}
	   public LambdaTest(String firstname, String lastname) {
	      this.firstname = firstname;
	      this.lastname = lastname;}
	   public void hello() {
	      System.out.println("Hello " + firstname + " " + lastname);}
	   public static void main(String[] args) {
		  LambdaTest hello = new LambdaTest("Avinash", "Ranjalkar");
	      hello.hello();
	   }*/
//with lambda
	   	interface HelloService {
		      String hello(String firstname, String lastname);
		   }

		   public static void main(String[] args) {		
			   
			HelloService helloService=(String firstname, String lastname) -> { 
				String hello="Hello " + firstname + " " + lastname; 
				return hello; 
			};
		    System.out.println(helloService.hello("Avinash", "Ranjalkar"));
		        

		  }
}
