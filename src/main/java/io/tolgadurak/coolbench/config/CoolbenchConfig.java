package io.tolgadurak.coolbench.config;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoolbenchConfig {
	private static Logger logger = LogManager.getLogger(CoolbenchConfig.class);
	private Integer nTimes;
	private Integer nThreads;
	private String algorithm;
	private static CoolbenchConfig config;

	public static CoolbenchConfig getConfig() {
		if (config == null) {
			config = init();
		}
		return config;
	}

	@JsonCreator
	private CoolbenchConfig(@JsonProperty(required = true, value = "nTimes") Integer nTimes,
			@JsonProperty(required = true, value = "nThreads") Integer nThreads,
			@JsonProperty(required = true, value = "algorithm") String algorithm) {
		this.nTimes = nTimes;
		this.nThreads = nThreads;
		this.algorithm = algorithm;
	}

	public Integer getnTimes() {
		return nTimes;
	}

	public void setnTimes(Integer nTimes) {
		this.nTimes = nTimes;
	}

	public Integer getnThreads() {
		return nThreads;
	}

	public void setnThreads(Integer nThreads) {
		this.nThreads = nThreads;
	}

	public String getAlgorithm() {
		return config.algorithm;
	}

	private static CoolbenchConfig init() {
		ObjectMapper mapper = new ObjectMapper();
		File configFile = getConfigFile();
		CoolbenchConfig config = null;
		try {
			config = mapper.readValue(configFile, CoolbenchConfig.class);
		} catch (IOException e) {
			logger.error("Error while deserializing JSON. File name: " + configFile.getName(), e);
			throw new RuntimeException();
		}
		return config;
	}

	private static File getConfigFile() {
		ClassLoader classLoader = CoolbenchConfig.class.getClassLoader();
		return new File(classLoader.getResource("coolbench.json").getFile());
	}

}
