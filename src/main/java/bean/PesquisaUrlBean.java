package bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import model.PesquisaURL;

public class PesquisaUrlBean {

	private PesquisaURL pesquisaUrl;

//	public PesquisaUrlBean() {
//		pesquisaUrl = new PesquisaURL();
//	}

	private static BufferedReader reader;

	public URL digitarURL(String urlString) {
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}

	public StringBuilder lerConteudoURL(URL url) throws IOException {
		StringBuilder conteudo = null;
		// open the URL stream, wrap it an a few "readers"
		BufferedReader reader;

		reader = new BufferedReader(new InputStreamReader(url.openStream()));

		String line;
		conteudo = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			conteudo.append(line + "\n");
		}

		reader.close();

		return conteudo;
	}

	public static void lerConteudoURL(String urlString) throws MalformedURLException, IOException {

		// create the url
		URL url = new URL(urlString);

		// open the URL stream, wrap it an a few "readers"
		reader = new BufferedReader(new InputStreamReader(url.openStream()));

		// write the output to stout
		imprimirConteudoTextual(reader);
	}

	public static void imprimirConteudoTextual(BufferedReader reader) throws IOException {
		String line;
		StringBuilder conteudo = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			conteudo.append(line + "\n");
		}

		System.out.print(conteudo);

		reader.close();
	}

}
