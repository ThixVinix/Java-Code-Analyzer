package utilitarios;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryUtil {

	private DirectoryUtil() {
	}
	
	public static File criarArquivo(String pathFileString) throws IOException {
		File file = new File(pathFileString);
		try {
			if (!file.createNewFile()) {
				throw new IOException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * Verifica se o diretório existe e se está completo (incluindo o arquivo alvo).
	 * 
	 * @param caminho
	 * @return true or false
	 * @throws SecurityException
	 */
	public static boolean validarDiretorioCompleto(Path caminho) {

		boolean isValid;

		try {
			isValid = caminho.toFile().exists() && caminho.isAbsolute();
		} catch (SecurityException e) {
			isValid = false;
		}
		return isValid;
	}

	public static boolean isJavaFile(File arquivo) {
		return arquivo.getName().endsWith(Constante.ARQUIVO_TIPO_JAVA);
	}

	public static File[] procurarArquivosJava(File diretorio) {
		File[] listFiles = diretorio.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return isJavaFile(pathname);
			}
		});

		return listFiles;
	}

	/**
	 * Retorna um diretório(path) através de um diretório(String).
	 * 
	 * @param diretorioString
	 * @return path or null
	 * @throws InvalidPathException
	 */
	public static Path obterPathPeloDiretorioString(String diretorioString) {

		Path path;

		try {
			path = Paths.get(diretorioString);
		} catch (InvalidPathException e) {
			path = null;
		}

		return path;
	}

	/**
	 * Retorna um arquivo através do caminho(path).
	 * 
	 * @param caminho
	 * @return file or null
	 */
	public static File obterArquivoPorDiretorio(Path caminho) {
		File arquivo;

		try {
			arquivo = caminho.toFile();
		} catch (Exception e) {
			arquivo = null;
		}

		return arquivo;
	}

}
