package info.zhiji.analysis.job;

import info.zhiji.analysis.db.mapper.DeviceMapper;
import info.zhiji.analysis.domain.entity.Device;
import info.zhiji.analysis.service.impl.TrtEfficiencyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mingyun.cjl
 * @date 2023/07/17
 */
@Component
@EnableScheduling
@Slf4j
public class TrtEfficiencyJob {

    /**
     * 并行计算线程池
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private TrtEfficiencyServiceImpl trtEfficiencyServiceImpl;

//    @Value("${analysis.deviceTypeCode}")
//    private String deviceTypeCode;

//    @Scheduled(cron = "0 0 3 * * ? ")
//    public void startInitJob() {
//        log.info("TrtEfficiencyJob startInitJob begin");
//        Device query = new Device();
//        query.setDeviceTypeCodesExclude(Lists.newArrayList(Constants.DEVICE_TYPE_CODE_RFL));
////        query.setDeviceTypeCode(deviceTypeCode);
//        List<Device> deviceList = deviceMapper.selectByCondition(query);
//        if (!CollectionUtils.isEmpty(deviceList)) {
//            for (Device device : deviceList) {
//                // executorService.execute(() -> {
//                    log.info("TrtEfficiencyJob startInitJob start deviceId=" + device.getId());
//                    try {
//                        trtEfficiencyServiceImpl.init(device.getId());
//                        log.info("TrtEfficiencyJob startInitJob stop deviceId=" + device.getId());
//                    } catch (Exception e) {
//                        log.error("TrtEfficiencyJob startInitJob stop with exceptions deviceId=" + device.getId(), e);
//                    }
//                // });
//            }
//        }
//        log.info("TrtEfficiencyJob startInitJob end");
//    }
      /*todo 邱豫春 2024-09-13 13:22:22 实时*/
//      @Scheduled(cron = "0/10 * * * * ?")
//    @Scheduled(cron = "0 0/5 * * * ? ")
    public void startRtJob() {
        log.info("TrtEfficiencyJob startRtJob begin");
        Device query = new Device();
        // query.setDeviceTypeCodesExclude(Lists.newArrayList(Constants.DEVICE_TYPE_CODE_RFL));
//        query.setDeviceTypeCode(deviceTypeCode);
        List<Device> deviceList = deviceMapper.selectByCondition(query);
        if (!CollectionUtils.isEmpty(deviceList)) {
            for (Device device : deviceList) {
                log.info("TrtEfficiencyJob startRtJob start deviceId=" + device.getId());
                trtEfficiencyServiceImpl.rt(device.getId());
                log.info("TrtEfficiencyJob startRtJob stop deviceId=" + device.getId());
            }
        }
        log.info("TrtEfficiencyJob startRtJob end");
    }

//    @Scheduled(cron = "0 0 0/6 * * ? ")
    public void startRepJob() {
        log.info("TrtEfficiencyJob startRepJob begin");
        Device query = new Device();
        // query.setDeviceTypeCodesExclude(Lists.newArrayList(Constants.DEVICE_TYPE_CODE_RFL));
//        query.setDeviceTypeCode(deviceTypeCode);
        List<Device> deviceList = deviceMapper.selectByCondition(query);
        if (!CollectionUtils.isEmpty(deviceList)) {
            for (Device device : deviceList) {
                // executorService.execute(() -> {
                    log.info("TrtEfficiencyJob startRepJob start deviceId=" + device.getId());
                    try {
                        boolean replaceNew = false;
                        trtEfficiencyServiceImpl.rep(device.getId(),replaceNew);
                        log.info("TrtEfficiencyJob startRepJob stop deviceId=" + device.getId());
                    } catch (Exception e) {
                        log.error("TrtEfficiencyJob startRepJob stop with exceptions deviceId=" + device.getId(), e);
                    }
                // });
            }
        }
        log.info("TrtEfficiencyJob startRepJob end");
    }

//    @Scheduled(cron = "0 0 2 * * ? ")
    public void startQuantifyJob() {
        log.info("TrtEfficiencyJob startQuantifyJob begin");
        Device query = new Device();
        // query.setDeviceTypeCodesExclude(Lists.newArrayList(Constants.DEVICE_TYPE_CODE_RFL));
//        query.setDeviceTypeCode(deviceTypeCode);
        List<Device> deviceList = deviceMapper.selectByCondition(query);
        if (!CollectionUtils.isEmpty(deviceList)) {
            for (Device device : deviceList) {
                // executorService.execute(() -> {
                    log.info("TrtEfficiencyJob startQuantifyJob start deviceId=" + device.getId());
                    try {
                        boolean isFinish = trtEfficiencyServiceImpl.quantify(device.getId());
                        log.info("TrtEfficiencyJob startQuantifyJob stop deviceId={},isFinish={}", device.getId(), isFinish);
                    } catch (Exception e) {
                        log.error("TrtEfficiencyJob startQuantifyJob stop with exceptions deviceId=" + device.getId(), e);
                    }
                // });
            }
        }
        log.info("TrtEfficiencyJob startQuantifyJob end");
    }
//    操作建议模块
//    @Scheduled(cron = "0 0 0/6 * * ? ")
    public void startSugJob() {
        log.info("TrtEfficiencyJob startSugJob begin");
        Device query = new Device();
        // query.setDeviceTypeCodesExclude(Lists.newArrayList(Constants.DEVICE_TYPE_CODE_RFL));
//        query.setDeviceTypeCode(deviceTypeCode);
        List<Device> deviceList = deviceMapper.selectByCondition(query);
        if (!CollectionUtils.isEmpty(deviceList)) {
            for (Device device : deviceList) {
                // executorService.execute(() -> {
                    log.info("TrtEfficiencyJob startSugJob start deviceId=" + device.getId());
                    try {
                        trtEfficiencyServiceImpl.sug(device.getId());
                        log.info("TrtEfficiencyJob startSugJob stop deviceId=" + device.getId());
                    } catch (Exception e) {
                        log.error("TrtEfficiencyJob startSugJob stop with exceptions deviceId=" + device.getId(), e);
                    }
                // });
            }
        }
        log.info("TrtEfficiencyJob startSugJob end");
    }

    public static void main(String[] args) {
        OLSMultipleLinearRegression oregression = new OLSMultipleLinearRegression();
        double[] y = new double[]{0.13796472510706345, 0.13795176623221445, 0.14156437410772635, 0.13686072901418708, 0.14260928921975133, 0.1368838253116463, 0.1367674514057536, 0.1409187328452924, 0.14203582278080149, 0.13819801828648487, 0.1367932735032587, 0.2723963333160909, 0.13658170536066952, 0.13760621788238267, 0.13725326153088932, 0.14268260664665, 0.13658676310644738, 0.13632829936552385, 0.1371249335251943, 0.1363585538633558, 0.13942423521177763, 0.13626330478027845, 0.13937485868858762, 0.13602576484577975, 0.14523242737636347, 0.13694577085888035, 0.13688774402170353, 0.13819590676658952, 0.13910463406802756, 0.1371198992484479, 0.14233640918926052, 0.14271514496445148};
//        double[] y = new double[]{0.13796472510706345, 0.13795176623221445, 0.14156437410772635, 0.13686072901418708, 0.14260928921975133, 0.1368838253116463, 0.1367674514057536, 0.1409187328452924, 0.14203582278080149, 0.13819801828648487, 0.1367932735032587, 0.2723963333160909, 0.13658170536066952, 0.13760621788238267, 0.13725326153088932, 0.14268260664665, 0.13658676310644738, 0.13632829936552385, 0.1371249335251943, 0.1363585538633558, 0.13942423521177763, 0.13626330478027845, 0.13937485868858762, 0.13602576484577975, 0.14523242737636347, 0.13694577085888035, 0.13688774402170353, 0.13819590676658952, 0.13910463406802756, 0.1371198992484479, 0.14233640918926052, 0.14271514496445148, 0.13668676310644738};
        double[][] x = new double[32][4];
        x[0] =new double[]{22.83241844,15000,6879.27002,21.71503067};
        x[1] =new double[]{22.768116,15000,6904.299805,22.01498795};
        x[2] =new double[]{22.25208664,15000,6705.319824,21.65820885};
        x[3] =new double[]{22.71362495,15000,6891.47998,22.85146713};
        x[4] =new double[]{23.13964272,15000,6682.740234,24.64600182};
        x[5] =new double[]{22.47089005,15000,6899.410156,23.09906769};
        x[6] =new double[]{22.49305344,15000,6900.629883,22.07751846};
        x[7] =new double[]{21.91246414,15000,6682.129883,23.61099243};
        x[8] =new double[]{23.41562271,15000,6705.319824,23.12038994};
        x[9]=new double[]{22.95882416,15000,6890.259766,22.11547661};
        x[10]=new double[]{22.34649467,15000,6895.140137,22.91809082};
        x[11]=new double[]{20.326597989,15000,6787.109863,0.128262997};
        x[12]=new double[]{22.58603477,15000,6889.040039,23.01636696};
        x[13]=new double[]{22.56263733,15000,6896.970215,22.85570526};
        x[14]=new double[]{22.43649673,15000,6895.140137,22.15161896};
        x[15]=new double[]{23.62984276,15000,6593.629883,23.45620346};
        x[16]=new double[]{22.55641556,15000,6896.970215,21.8017807};
        x[17]=new double[]{22.34373474,15000,6895.75,22.54511833};
        x[18]=new double[]{23.15587616,15000,6895.75,22.60770988};
        x[19]=new double[]{22.74523735,15000,6910.399902,22.8831749};
        x[20]=new double[]{21.91322327,15000,6895.140137,21.8065834};
        x[21]=new double[]{22.68551826,15000,6904.299805,23.13222885};
        x[22]=new double[]{22.71255302,15000,6887.209961,22.63835907};
        x[23]=new double[]{22.49769974,15000,6895.140137,22.91137695};
        x[24]=new double[]{23.29343414,15000,6494.140137,23.51734924};
        x[25]=new double[]{22.71365738,15000,6885.990234,22.05454636};
        x[26]=new double[]{22.58603477,15000,6907.350098,23.01636696};
        x[27]=new double[]{22.6794796,15000,6890.259766,21.95773506};
        x[28]=new double[]{22.71115875,15000,6893.919922,22.0496273};
        x[29]=new double[]{23.9368248,15000,6895.140137,23.36836052};
        x[30]=new double[]{20.93498611,15000,6708.370117,23.6110878};
        x[31]=new double[]{22.52961159,15000,6878.660156,23.48874855};
//        x[32]=new double[]{21.85145187,15000,6699.220215,21.8017807};

        oregression.newSampleData(y, x);
        double[] beta = oregression.estimateRegressionParameters();
        for (double d : beta) {
            System.out.println(d);
        }

    }
}
