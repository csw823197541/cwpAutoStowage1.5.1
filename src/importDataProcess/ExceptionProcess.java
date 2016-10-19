package importDataProcess;

/**
 * Created by csw on 2016/10/11 14:27.
 * Explain:
 */
public class ExceptionProcess {

    public static String getExceptionInfo(Long batchNum) {
        String info = null;
        if (batchNum != null) {
            if (ExceptionData.exceptionMap != null) {
                info = ExceptionData.exceptionMap.get(batchNum);
            }
        } else {
            info = "批次号不能为null";
        }
        return info;
    }

    public static void clearExceptionInfo(Long batchNum) {
        if (batchNum != null) {
            ExceptionData.exceptionMap.put(batchNum, "");
        }
    }
}
