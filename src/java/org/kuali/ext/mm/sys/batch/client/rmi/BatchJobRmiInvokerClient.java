package org.kuali.ext.mm.sys.batch.client.rmi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.kuali.ext.mm.sys.batch.service.impl.BatchStatusVO;
import org.springframework.remoting.rmi.RmiInvocationWrapper_Stub;
import org.springframework.remoting.support.RemoteInvocation;

public class BatchJobRmiInvokerClient implements Serializable {
    private static final long serialVersionUID = -7731753674999195029L;
    private static final String DEFAULT_LOCALHOST = "localhost";
    private static final int DEFAULT_RMI_PORT_1099 = 1099;
    private static final String DEFAULT_KMM_BATCH_CONTROL_SERVICE = "kmmBatchControlService";
    private static final String DEFAULT_PERFORM_JOB_METHOD = "performJob";
    private static final String BATCH_RMI_SERVICE = "batch.rmi.service";
    private static final String BATCH_RMI_PORT = "batch.rmi.port";
    private static final String BATCH_RMI_HOST = "batch.rmi.host";
    private static final String KMM_BATCH_CLIENT_PROPERTIES = "kmm-batch-client.properties";
    private static Options options = new Options();
    static {
        options.addOption("j", true, "Batch job name.");
        options.addOption("h", true, "RMI host name.");
        options.addOption("p", true, "RMI port, default is 1099.");
        options.addOption("s", true, "Remote service name.");
        options.addOption("c", true, "Batch client configuration properties file path.");
        options.addOption("help", true, "Usage help");
    }

    public static void main(String[] args) {
        BatchStatusVO status = null;
        try {
            CommandLineParser parser = new PosixParser();
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp(BatchJobRmiInvokerClient.class.getName(), options);
                System.exit(0);
            }

            Properties props = new Properties();
            String kmmBatchClientProperties = KMM_BATCH_CLIENT_PROPERTIES;
            InputStream propsStream = null;
            if (cmd.hasOption("c") && cmd.getOptionValue("c") != null) {
                kmmBatchClientProperties = cmd.getOptionValue("c");
                propsStream = new FileInputStream(kmmBatchClientProperties);
            }
            else {
                propsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                        kmmBatchClientProperties);
            }
            if (propsStream != null) {
                props.load(propsStream);
            }
            // Get host name/ip
            String rmiHost = DEFAULT_LOCALHOST;
            if (cmd.hasOption("h")) {
                rmiHost = cmd.getOptionValue("h");
            }
            else if (props.containsKey(BATCH_RMI_HOST)) {
                // read from properties file
                rmiHost = props.getProperty(BATCH_RMI_HOST);
            }
            Integer rmiPort = DEFAULT_RMI_PORT_1099;
            if (cmd.hasOption("p")) {
                rmiPort = Integer.valueOf(cmd.getOptionValue("p"));
            }
            else if (props.containsKey(BATCH_RMI_PORT)) {
                // read from properties file
                rmiPort = Integer.valueOf(props.getProperty(BATCH_RMI_PORT));
            }
            String batchServiceName = DEFAULT_KMM_BATCH_CONTROL_SERVICE;
            if (cmd.hasOption("s")) {
                batchServiceName = cmd.getOptionValue("s");
            }
            else if (props.containsKey(BATCH_RMI_SERVICE)) {
                // read from properties file
                batchServiceName = props.getProperty(BATCH_RMI_SERVICE);
            }
            String jobName = "";
            if (cmd.hasOption("j")) {
                jobName = cmd.getOptionValue("j");
            }
            else {
                System.err
                        .print("ERROR: No Job is specified for execution, run using -j <job name>");
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp(BatchJobRmiInvokerClient.class.getName(), options);
                System.exit(1);
            }
            Registry registry = LocateRegistry.getRegistry(rmiHost, rmiPort);
            RmiInvocationWrapper_Stub service = (RmiInvocationWrapper_Stub) registry
                    .lookup(batchServiceName);
            RemoteInvocation data = new RemoteInvocation();
            data.setMethodName(DEFAULT_PERFORM_JOB_METHOD);
            data.setParameterTypes(new Class[] { String.class, Date.class });
            data.setArguments(new Object[] { jobName, new Date() });
            System.out.println(new Date() + " - Invoking " + rmiHost + ":" + rmiPort + "/"
                    + jobName);
            status = (BatchStatusVO) service.invoke(data);
            System.out.println(new Date() + " - Finished " + rmiHost + ":" + rmiPort + "/"
                    + jobName);
            System.exit(0);
        }
        catch (Throwable e) {
            System.err.println("ERROR: Error occurred while trying to run the Job with args="
                    + flattenArgs(args));
            e.printStackTrace(System.err);
            System.exit(2);
        }
        finally {
            if (status != null) {
                System.out.println("Job ended "
                        + (status.isSuccess() ? " successfully." : "with errors."));
                if (status.getLog4jMessage() != null) {
                    System.out.println("SERVER LOG BEGIN HERE----------------------");
                    System.out.println(status.getLog4jMessage());
                    System.out.println("SERVER LOG END HERE------------------------");
                }
            }
        }
    }

    /**
     * Flatten program arguments
     * 
     * @param args main arguments
     * @return Concatenated string
     */
    private static String flattenArgs(String[] args) {
        if (args == null) {
            return "none";
        }
        String retVal = "";
        for (String string : args) {
            retVal = retVal + " " + string;
        }
        return retVal;
    }
}
