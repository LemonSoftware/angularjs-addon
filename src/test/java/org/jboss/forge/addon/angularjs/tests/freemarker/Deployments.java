package org.jboss.forge.addon.angularjs.tests.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.jboss.forge.addon.angularjs.AngularScaffoldProvider;
import org.jboss.forge.addon.angularjs.DetailTemplateStrategy;
import org.jboss.forge.addon.angularjs.ExtJSException;
import org.jboss.forge.addon.angularjs.TestHelpers;
import org.jboss.forge.addon.angularjs.matchers.InspectionResultMatcher;
import org.jboss.forge.addon.angularjs.util.RestResourceTypeVisitor;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.jboss.forge.furnace.repositories.AddonDependencyEntry;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class Deployments {

     static Path tmpPath;

    static{
        try {
             tmpPath = Files.createTempDirectory(Paths.get(System.getProperty("user.dir")), "tmp-generated");
        } catch (IOException e) {
            System.err.println("Failed to create tmp dir: "+e.getMessage());
        }
    }


    public static void writeToTemp(String output, String fileName) throws IOException {
        IOUtils.write(output, new FileOutputStream(tmpPath + "/" + fileName));

    }

    public static ForgeArchive getDeployment() {
        return ShrinkWrap
                .create(ForgeArchive.class)
                .addClass(Deployments.class)
                .addClass(DetailTemplateStrategy.class)
                .addClass(InspectionResultMatcher.class)
                .addAsLibrary(Maven.resolver().resolve("org.jsoup:jsoup:1.7.1").withTransitivity().asSingleFile())
                .addPackage(AngularScaffoldProvider.class.getPackage())
				.addPackage(TestHelpers.class.getPackage())
                .addClass(RestResourceTypeVisitor.class)
                .addAsResources(Deployments.BASE_PACKAGE,
                        INDEX_FTL_HTML,
                        JS_ENTITY_FTL_CONTROLLER,
                        JS_ENTITY_FTL_MODEL,
                        JS_ENTITY_FTL_STORE,
                        JS_ENTITY_FTL_VIEW,
                        MAIN_FTL_JS,
                        VIEWPORT_FTL_JS, JS_ENTITY_FTL_STORE, NULLID_FTL_JS,
                        BASIC_PROPERTY_DETAIL, LOOKUP_PROPERTY_DETAIL, N_TO_MANY_PROPERTY_DETAIL,
                        N_TO_ONE_PROPERTY_DETAIL)
				.addBeansXML()
				.addAsAddonDependencies(
						AddonDependencyEntry
								.create("org.jboss.forge.furnace.container:cdi"),
						AddonDependencyEntry
								.create("org.jboss.forge.addon:scaffold-spi"),
						AddonDependencyEntry
								.create("org.jboss.forge.addon:javaee"),
						AddonDependencyEntry
								.create("org.jboss.forge.addon:templates"),
						AddonDependencyEntry
								.create("org.jboss.forge.addon:text"),
						AddonDependencyEntry
								.create("org.jboss.forge.addon:convert"),
						AddonDependencyEntry
								.create("org.jboss.forge.addon:parser-java"));
	}

	public static final Package BASE_PACKAGE = AngularScaffoldProvider.class
			.getPackage();
	public static final String BASE_PACKAGE_PATH = "/" + BASE_PACKAGE.getName().replace('.', '/') + File.separator;
    public static final String INDEX_FTL_HTML = "index.html.ftl";
    public static final String JS_ENTITY_FTL_CONTROLLER = "scripts/controller/entityController.js.ftl";
    public static final String JS_ENTITY_FTL_MODEL = "scripts/model/entityModel.js.ftl";
    public static final String JS_ENTITY_FTL_STORE = "scripts/store/entityStore.js.ftl";
    public static final String JS_ENTITY_FTL_VIEW = "scripts/view/entityGrid.js.ftl";
    public static final String MAIN_FTL_JS = "scripts/main.js.ftl";
    public static final String VIEWPORT_FTL_JS = "scripts/Viewport.js.ftl";
    public static final String NULLID_FTL_JS = "scripts/NullIdGenerator.js.ftl";

    public static final String BASIC_PROPERTY_DETAIL = "scripts/view/includes/basicPropertyDetail.js.ftl";
    public static final String LOOKUP_PROPERTY_DETAIL = "scripts/view/includes/lookupPropertyDetail.js.ftl";
    public static final String N_TO_MANY_PROPERTY_DETAIL = "scripts/view/includes/nToManyPropertyDetail.js.ftl";
    public static final String N_TO_ONE_PROPERTY_DETAIL = "scripts/view/includes/nToOnePropertyDetail.html.ftl";


}
