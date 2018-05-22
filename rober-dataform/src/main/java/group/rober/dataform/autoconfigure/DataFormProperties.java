package group.rober.dataform.autoconfigure;

import group.rober.runtime.kit.ListKit;
import group.rober.runtime.kit.StringKit;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

@ConfigurationProperties(prefix = "group.rober.dataform", ignoreUnknownFields = true)
public class DataFormProperties {
    public static final String TYPE_XML = "xml";
    public static final String TYPE_DATABASE = "database";

    private String dataformTagName = "dataform";
    private String resourcePathTpl = "{0}/src/main/resources";
    private boolean authResourcePath = true;    //自动计算处理资源文件路径
    private String resourcePath = "/dataform";
    private String dataFormMapperType = TYPE_DATABASE;

    private List<String> autoIncludes = ListKit.listOf(
            "/dataform/macro/fieldset.ftl"
            , "/dataform/macro/field/field.ftl"
            , "/dataform/macro/field/text.ftl"
            , "/dataform/macro/field/radio.ftl"
            , "/dataform/macro/field/date.ftl"
            , "/dataform/macro/field/number.ftl"
            , "/dataform/macro/field/money.ftl"
            , "/dataform/macro/field/money.ftl"
            , "/dataform/macro/field/picker.ftl"
            , "/dataform/macro/field/select.ftl"
            , "/dataform/macro/field/datemonth.ftl"
            , "/dataform/macro/field/richtext.ftl"
            , "/dataform/macro/detail-info.ftl"
    );

    private String exportDir;
    private String importDir;
    private String dictionaryDir = "/dictionary";
    private String dataformDir = "/dataform";

    @PostConstruct
    public void init(){
        if(authResourcePath){
            String runDir = this.getClass().getClassLoader().getResource("").getFile();
            if(runDir.endsWith("classes/")){
                File file = new File(runDir);
                String basePath = file.getParentFile().getParentFile().getAbsolutePath();
                resourcePath = StringKit.format(resourcePathTpl,basePath);
            }
        }
    }

    public String getDataformTagName() {
        return dataformTagName;
    }

    public void setDataformTagName(String dataformTagName) {
        this.dataformTagName = dataformTagName;
    }

    public String getResourcePathTpl() {
        return resourcePathTpl;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public List<String> getAutoIncludes() {
        return autoIncludes;
    }

    public void setAutoIncludes(List<String> autoIncludes) {
        this.autoIncludes = autoIncludes;
    }

    public String getDataFormMapperType() {
        return dataFormMapperType;
    }

    public void setDataFormMapperType(String dataFormMapperType) {
        this.dataFormMapperType = dataFormMapperType;
    }

    public String getExportDir() {
        return exportDir;
    }

    public void setExportDir(String exportDir) {
        this.exportDir = exportDir;
    }

    public String getImportDir() {
        return importDir;
    }

    public void setImportDir(String importDir) {
        this.importDir = importDir;
    }

    public String getDictionaryDir() {
        return dictionaryDir;
    }

    public void setDictionaryDir(String dictionaryDir) {
        this.dictionaryDir = dictionaryDir;
    }

    public String getDataformDir() {
        return dataformDir;
    }

    public void setDataformDir(String dataformDir) {
        this.dataformDir = dataformDir;
    }
}
