package utilitarios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectoryUtil {

	private static final Logger LOGGER = LogManager.getLogger(DirectoryUtil.class.getName());

	private static Class<DirectoryUtil> c;
	private static Method[] methods;

	static {
		c = DirectoryUtil.class;
		methods = c.getMethods();
	}

	private DirectoryUtil() {
	}

	public static BufferedReader lerArquivo(File arquivo) {

		BufferedReader br;
		String message = "Leitura realizada.";

		try {

			LOGGER.info("Inicializando leitura do arquivo...");

			if (Util.isNullOrEmpty(arquivo))
				throw new NullPointerException();

			FileInputStream stream = new FileInputStream(arquivo);
			InputStreamReader reader = new InputStreamReader(stream);
			br = new BufferedReader(reader);
			
			LOGGER.info(message);
		} catch (NullPointerException e) {
			message = String.format("Não foi possível ler arquivo nulo: %s", e.getCause());
			LOGGER.warn(message);
			br = null;
		} catch (FileNotFoundException e) {
			message = String.format("Não foi possível encontrar arquivo \"%s\": %s", arquivo.getName(), e.getMessage());
			LOGGER.warn(message);
			br = null;
		}

		return br;
	}

	public static File criarArquivo(String pathFileString) {

		File file;
		String message = "Criação efetuada";
//		String nomeMetodo = getCompleteMethodName("criarArquivo");

		try {
			LOGGER.debug("Criando arquivo...");
			if (Util.isNullOrEmpty(pathFileString))
				throw new NullPointerException();

			file = new File(pathFileString);

			if (!file.createNewFile())
				throw new IOException();

			LOGGER.info(message);
		} catch (NullPointerException e) {
			message = String.format("Não foi possível criar o arquivo por um diretório vazio/nulo %n %n %s",
					e.getMessage());
			LOGGER.warn(message);
			file = null;
		} catch (IOException e) {
			message = String.format("Não foi possível criar o arquivo pelo diretório \"%s\"%n %s", pathFileString,
					e.getMessage());
			LOGGER.warn(message);
			file = null;
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
			LOGGER.debug("Convertendo String para Path...");
			path = Paths.get(diretorioString);
			LOGGER.info("Path obtido.");
		} catch (InvalidPathException e) {
			String message;
			if (Util.isNullOrEmpty(diretorioString)) {
				message = String.format("Não foi possível obter o diretório vazio/nulo: %s", e.getCause());
				LOGGER.warn(message);
			} else {
				message = String.format("Não foi possível obter o diretório: \"%s\"%n: %s", diretorioString,
						e.getMessage());
				LOGGER.warn(message);
			}
			path = null;
		}

		return path;
	}

	public static Path obterPathPorArquivoExistente(File file) {

		Path path;
		String message = "Path obtido.";
//		String nomeMetodo = getCompleteMethodName("obterPathPorArquivoExistente");

		try {
			LOGGER.debug("Convertendo File para Path...");
			path = file.toPath();
			LOGGER.info(message);
		} catch (NullPointerException e) {
			message = String.format("Não é possível obter o caminho de um arquivo vazio/nulo: %s", e.getCause());
			path = null;
			LOGGER.warn(message);
		} catch (InvalidPathException e) {
			message = String.format("Não foi possível obter o diretório do arquivo: %s %n %s", file.toString(),
					e.getMessage());
			LOGGER.warn(message);
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
		File arquivo = null;
		String message = "Arquivo obtido.";

		try {
			LOGGER.info("Obtendo arquivo através de um diretório...");
			arquivo = caminho.toFile();
			LOGGER.info(message);
		} catch (NullPointerException e) {
			message = String.format("Não foi possível obter o arquivo através de um diretório vazio/nulo: %s",
					e.getCause());
			LOGGER.warn(message);
		} catch (UnsupportedOperationException e) {
			message = String.format("Não foi possível obter o diretório através do diretório digitado: %s %n %s",
					caminho.toString(), e.getMessage());
			LOGGER.warn(message);
			arquivo = null;
		}

		return arquivo;
	}

	@SuppressWarnings(value = "unused")
	private static String getCompleteMethodName(String nameMethodString) {
		for (Method method : methods) {
			if (nameMethodString.equalsIgnoreCase(method.getName())) {
				return method.toGenericString();
			}
		}
		return Constante.VAZIO;
	}

}
