package group.rober.base.autoconfigure;

import group.rober.runtime.kit.ListKit;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "group.rober.base", ignoreUnknownFields = true)
public class BaseProperties {

    private String viewPath = "/views";

    private String numberFormat = "#";

    private List<String> autoIncludes = ListKit.listOf("/base/macro/body.ftl","/base/macro/pagelet.ftl");

    private boolean productionModel = false;    /*是否生产模式*/

    private long multipartMaxFileSize = 1024L * 1024L * 100;//最大上传文件大小，100M

    public long getMultipartMaxFileSize() {
        return multipartMaxFileSize;
    }

    public void setMultipartMaxFileSize(long multipartMaxFileSize) {
        this.multipartMaxFileSize = multipartMaxFileSize;
    }

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public List<String> getAutoIncludes() {
        return autoIncludes;
    }

    public void setAutoIncludes(List<String> autoIncludes) {
        this.autoIncludes = autoIncludes;
    }

    public boolean isProductionModel() {
        return productionModel;
    }

    public void setProductionModel(boolean productionModel) {
        this.productionModel = productionModel;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }
}
