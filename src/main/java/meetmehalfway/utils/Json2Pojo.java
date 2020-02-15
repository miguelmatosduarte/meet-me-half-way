package meetmehalfway.utils;

import java.io.File;

import java.io.IOException;

import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;

import org.jsonschema2pojo.GenerationConfig;

import org.jsonschema2pojo.Jackson2Annotator;

import org.jsonschema2pojo.SchemaGenerator;

import org.jsonschema2pojo.SchemaMapper;

import org.jsonschema2pojo.SchemaStore;

import org.jsonschema2pojo.SourceType;

import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class Json2Pojo {

    public static void main(String[] args) {

        String packageName="meetmehalfway.model.api.result";

        File inputJson= new File("/Users/duartem2/Documents/TECH/repos/github/miguelmatosduarte/meet-me-half-way/src/main/resources/templates/result.json");

        File outputPojoDirectory=new File("src/main/java/");

        outputPojoDirectory.mkdirs();

        try {

            new Json2Pojo().convert2JSON(inputJson.toURI().toURL(), outputPojoDirectory, packageName, inputJson.getName().replace(".json", ""));

        } catch (IOException e) {

            // TODO Auto-generated catch block

            System.out.println("Encountered issue while converting to pojo: "+e.getMessage());

            e.printStackTrace();

        }

    }

    void convert2JSON(URL inputJson, File outputPojoDirectory, String packageName, String className) throws IOException{

        JCodeModel codeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {

            @Override

            public boolean isGenerateBuilders() { // set config option by overriding method

                return true;

            }

            public SourceType getSourceType(){

                return SourceType.JSON;

            }

        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());

        mapper.generate(codeModel, className, packageName, inputJson);

        codeModel.build(outputPojoDirectory);

    }

}
