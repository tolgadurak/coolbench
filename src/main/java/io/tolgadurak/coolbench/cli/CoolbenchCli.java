package io.tolgadurak.coolbench.cli;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.tolgadurak.coolbench.api.CoolbenchApi;
import io.tolgadurak.coolbench.config.CoolbenchConfig;

public class CoolbenchCli {

	private static Logger logger = LogManager.getLogger(CoolbenchCli.class);

	@Parameter(names = { "--times", "-t" })
	private Integer nTimes;
	@Parameter(names = { "--threads", "-T" })
	private Integer nThreads;
	@Parameter(names = { "--algorithm", "-a" })
	private String algorithm;
	private CoolbenchApi coolbenchApi = new CoolbenchApi();
	private CoolbenchConfig config = CoolbenchConfig.getConfig();

	public static CoolbenchCli initJCommander(String[] args) {
		CoolbenchCli main = new CoolbenchCli();
		JCommander.newBuilder().addObject(main).build().parse(args);
		return main;
	}

	public static void main(String[] args) throws InterruptedException {
		CoolbenchCli main = initJCommander(args);
		main.loadParameters();
		main.run();
	}

	public void run() throws InterruptedException {
		String jsonResult = null;
		try {
			jsonResult = coolbenchApi.doBenchmark(nTimes, nThreads, algorithm).toJson();
		} catch (JsonProcessingException e) {
			logger.error("Error while serializing coolbench result to json", e);
		}
		logger.info(jsonResult);
	}

	private void loadParameters() {
		if (nTimes == null) {
			nTimes = config.getnTimes();
		}
		if (nThreads == null) {
			nThreads = config.getnThreads();
		}
		if (StringUtils.isEmpty(algorithm)) {
			algorithm = config.getAlgorithm();
		}
	}
}
