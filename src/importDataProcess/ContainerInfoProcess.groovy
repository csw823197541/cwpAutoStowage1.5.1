package importDataProcess

import groovy.json.JsonSlurper
import importDataInfo.ContainerInfo

/**
 * 解析在场箱信息
 * Created by csw on 2016/1/15.
 */
class ContainerInfoProcess {

    //Json字符串解析编码
    public static List<ContainerInfo> getContainerInfo(String jsonStr) {

        boolean isError = false;
        List<ContainerInfo> containerInfoList = new ArrayList<ContainerInfo>();
        String IYCCNTRNO = null;
        try{
            def root = new JsonSlurper().parseText(jsonStr)
            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List
            root.each {container->
                ContainerInfo containerInfo = new ContainerInfo()
                assert container instanceof Map
                IYCCNTRNO = container.IYCCNTRNO
                containerInfo.IYCCNTRNO = container.IYCCNTRNO
                containerInfo.containerId = container.IYCCNTRNO
                containerInfo.IYCCNTRAREAID = container.IYCCNTRAREAID
                containerInfo.IYCVOYID = container.IYCVOYID
                containerInfo.IYCCTYPECD = container.IYCCTYPECD
                containerInfo.IYCCSZCSIZECD = container.IYCCSZCSIZECD
                containerInfo.IYCPORTCD = container.IYCPORTCD//目的港
                containerInfo.IYCWEIGHT = Integer.valueOf(container.IYCWEIGHT)
                containerInfo.IYCDNGFG = container.IYCDNGFG
                containerInfo.IYCREFFG = container.IYCREFFG
                containerInfo.IYCYLOCATION = container.IYCYLOCATION
                containerInfo.IYCPLANFG = container.IYCPLANFG
                containerInfo.IYCRETIME = container.IYCRETIME
                containerInfoList.add(containerInfo)
            }
        }
        catch (Exception e){
            System.out.println("在场箱信息解析时，发现json数据异常！" + IYCCNTRNO)
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("在场箱信息解析失败！")
            return null;
        }else {
            System.out.println("在场箱信息解析成功！")
            return containerInfoList
        }
    }

}
