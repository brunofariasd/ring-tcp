
 import java.util.Scanner;

 public class RodarProcesso {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.print("Numero do Processo: ");
		int numero = in.nextInt();

		System.out.print("Server Port: ");
		int sPort = in.nextInt();

		System.out.print("Client Port: ");
		int cPort = in.nextInt();

		new Processo("127.0.0.1", numero, sPort, cPort).execute();
		
	}
 }
