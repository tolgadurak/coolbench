package io.tolgadurak.coolbench.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoolbenchConfig {
	private static Logger logger = LogManager.getLogger(CoolbenchConfig.class);
	private Integer nTimes;
	private String algorithm;
	private static CoolbenchConfig config;

	public static CoolbenchConfig getConfig() {
		if (config == null) {
			try {
				config = init();
			} catch (UnsupportedEncodingException e) {
				logger.error("Error while decoding config file name");
			}
		}
		return config;
	}

	@JsonCreator
	private CoolbenchConfig(@JsonProperty(required = true, value = "nTimes") Integer nTimes,

			@JsonProperty(required = true, value = "algorithm") String algorithm) {
		this.nTimes = nTimes;

		this.algorithm = algorithm;
	}

	public Integer getnTimes() {
		return nTimes;
	}

	public void setnTimes(Integer nTimes) {
		this.nTimes = nTimes;
	}

	public String getAlgorithm() {
		return config.algorithm;
	}

	private static CoolbenchConfig init() throws UnsupportedEncodingException {
		ObjectMapper mapper = new ObjectMapper();
		InputStream configFileAsStream = getConfigFileAsStream();
		CoolbenchConfig config = null;
		try {
			config = mapper.readValue(configFileAsStream, CoolbenchConfig.class);
		} catch (IOException e) {
			logger.error("Error while deserializing JSON. File name: coolbench.json", e);
			throw new RuntimeException();
		}
		return config;
	}

	private static InputStream getConfigFileAsStream() throws UnsupportedEncodingException {
		ClassLoader classLoader = CoolbenchConfig.class.getClassLoader();
		return classLoader.getResourceAsStream("coolbench.json");
	}

}
