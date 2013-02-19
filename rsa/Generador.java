package rsa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JTextArea;

import log.Identificador;

public class Generador {
	
	KeyPairGenerator keyGen;
	PrivateKey priv;
	PublicKey pub;
	ObjectOutputStream salida;
	Cipher rsaCipher;
	JTextArea texto;
//	==================================================================		
	public Generador(JTextArea texto) 
//	==================================================================	
	{
		this.texto = texto;
	}
//	==================================================================	
	public void generarNumSerie(String remesa,String serie)
//	==================================================================	
	{
		texto.append("\nremesa : "+remesa+
				"\nnum. serie : "+serie);
		Identificador id = new Identificador();
		id.setNumSerie(serie);
		id.setRemesa(remesa);
		
		try {
			salida = new ObjectOutputStream(new FileOutputStream("serial.obj"));
			salida.writeObject(id);
			salida.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Comprobar
		try {
			ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("serial.obj"));
			Identificador obj1;
			obj1 = (Identificador)entrada.readObject();
			texto.append("\nComprobacion serial generado:\nremesa : "+
					obj1.getRemesa()+"\nnum. serie : "+
					obj1.getNumSerie());
			entrada.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
//	==================================================================		
	public void generarKeys()
//	==================================================================	
	{	
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random;
			random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(1024, random);
			KeyPair pair = keyGen.generateKeyPair();
			priv = pair.getPrivate();
			pub = pair.getPublic();
			texto.append("Llave publica generada:\n"+pub.toString()+"\n\n");
			texto.append("Llave privada generada:\n"+priv.toString()+"\n\n");			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		//Escribo la clave privada
		try {
			salida = new ObjectOutputStream(new FileOutputStream("priKey.obj"));
			salida.writeObject(priv);
			salida.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		Escribo la clave publica
		try {
			salida = new ObjectOutputStream(new FileOutputStream("pubKey.obj"));
			salida.writeObject(pub);
			salida.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		Leer Keys
		try {
			ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("pubKey.obj"));
			PublicKey obj1;
			obj1 = (PublicKey)entrada.readObject();
			texto.append("\n\nComprobacion llave publica generada:\n"+obj1.toString());
			entrada.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//*/
	}
//	==================================================================	
	public void generarLicencia(String remesa,String serial,String hdd) 
//	==================================================================	
	{
		Identificador id = null;
		try {
			ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("priKey.obj"));
			priv = (PrivateKey)entrada.readObject();
			entrada.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FileOutputStream fw;
		try {
			fw = new FileOutputStream("licencia.lic");
		
			rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			rsaCipher.init(Cipher.ENCRYPT_MODE, priv);
			String textoClaro = remesa+"-"+serial+"-"+hdd;
			byte[] cleartext = null;
			cleartext = textoClaro.getBytes();
			texto.append("\nEl texto a encriptar es :\n"+print(cleartext)+"\n\n");
//			Encrypt the cleartext
			byte[] ciphertext = null;
			ciphertext = rsaCipher.doFinal(cleartext);
			texto.append("El texto encriptado :\n");
			fw.write(ciphertext);
			String licencia = new String("");
			for(int i =0;i<ciphertext.length;i++){
				licencia+=(""+ciphertext[i]);
			}
			/*
			 */
			texto.append(licencia);
			texto.append("\n\n");
			fw.close();	
			
			//Testeo la licencia generada
			
			//Leer clave publica 
			try {
				ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("pubKey.obj"));
				pub = (PublicKey)entrada.readObject();
				entrada.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			FileInputStream fr = new FileInputStream("licencia.lic");
			byte[] buffer = new byte[128];
			fr.read(buffer);
			Cipher rsaCipher2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			rsaCipher2.init(Cipher.DECRYPT_MODE, pub);
			
//			Decrypt the ciphertext/*
			byte[] cleartext1 = rsaCipher2.doFinal(buffer);

			texto.append(new String(cleartext1, "8859_1"));
			
			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//	==================================================================	
	protected String print(byte[] in)
//	==================================================================	
	{
		String out = "";
		try {
			out = new String(in, "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return out;
	}
	protected void printBytes(byte[] in){
		for(int i =0;i<in.length;i++){
			System.out.print(in[i]);
		}
		System.out.println("");
	}	
	
	
}


/*
 	public void testGenerador() {
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(1024, random);
			KeyPair pair = keyGen.generateKeyPair();
			PrivateKey priv = pair.getPrivate();
			//public key es un objeto serializable !!!!
			PublicKey pub = pair.getPublic();
			System.out.println("public->"+pub.toString());
			System.out.println("private->"+priv.toString());
			
			/* Create the cipher 
			Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//			Initialize the cipher for encryption
			rsaCipher.init(Cipher.ENCRYPT_MODE, pub);
			
//			Cleartext
			byte[] cleartext = null;
			cleartext = "This is Bilal".getBytes();
//			byte[] cleartext = “This is Bilal".getBytes();
//			String cleartext = “This is just an example";
			System.out.println("the original cleartext is: "+print(cleartext));
			
			
//			System.out.println("the original cleartext is: " + cleartext);
			
//			Encrypt the cleartext
			byte[] ciphertext = null;
			ciphertext = rsaCipher.doFinal(cleartext);
//			byte[] ciphertext = rsaCipher.doFinal(cleartext);
//			String ciphertext = rsaCipher.doFinal(cleartext);
			System.out.println("the encrypted text is: " );
			printBytes(ciphertext);
//			Initialize the same cipher for decryption
			rsaCipher.init(Cipher.DECRYPT_MODE, priv);
			
//			Decrypt the ciphertext
			byte[] cleartext1 = rsaCipher.doFinal(ciphertext);
//			String cleartext1 = rsaCipher.doFinal(ciphertext);
			System.out.println("the final cleartext is: "+print(cleartext1));
			
			System.out.println("");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch(NoSuchProviderException e){
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}catch(InvalidKeyException e){
			e.printStackTrace();
		}catch(NoSuchPaddingException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		}
	}
	*/
