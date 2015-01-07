/**
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.angularjs.tests.freemarker;

import org.hamcrest.core.IsEqual;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;
import org.jboss.forge.arquillian.AddonDependency;
import org.jboss.forge.arquillian.Dependencies;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.metawidget.util.simple.StringUtils;

import javax.inject.Inject;

import java.net.URL;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.jboss.forge.addon.angularjs.TestHelpers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Tests to verify that the generated HTML for basic properties of JPA entities are generated correctly.
 */
@RunWith(Arquillian.class)
public class FreemarkerClientPartialsBasicPropertyTest {

    @Inject
    private ResourceFactory resourceFactory;

    @Inject
    private TemplateFactory processorFactory;

    @Deployment
    @Dependencies({
            @AddonDependency(name = "org.jboss.forge.addon:scaffold-spi"),
            @AddonDependency(name = "org.jboss.forge.addon:javaee"),
            @AddonDependency(name = "org.jboss.forge.addon:templates"),
            @AddonDependency(name = "org.jboss.forge.addon:text"),
            @AddonDependency(name = "org.jboss.forge.addon:convert"),
            @AddonDependency(name = "org.jboss.forge.addon:parser-java")
    })
    public static ForgeArchive getDeployment()
    {
        return Deployments.getDeployment();
    }
    
    @Test
    public void testGenerateHiddenProperty() throws Exception {
        Map<String, Object> root = createInspectionResultWrapper(ENTITY_NAME, ENTITY_VERSION_PROP);

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.BASIC_PROPERTY_DETAIL));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);
        assertThat(output.trim(), IsEqual.equalTo(""));
    }

    @Test
    public void testGenerateHiddenAndRequiredProperty() throws Exception {
        Map<String, Object> root = createInspectionResultWrapper(ENTITY_NAME, ENTITY_ID_PROP);

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.BASIC_PROPERTY_DETAIL));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);
        assertThat(output.trim(), IsEqual.equalTo(""));
    }

    @Test
    public void testGenerateBasicStringProperty() throws Exception {
        Map<String, Object> root = createInspectionResultWrapper(ENTITY_NAME, BASIC_STRING_PROP);

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.BASIC_PROPERTY_DETAIL));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);


        assertTrue(output.contains("header:"));
        assertTrue(output.contains("fullName"));


    }


    @Test
    public void testGenerateBasicNumberProperty() throws Exception {
        Map<String, Object> root = createInspectionResultWrapper(ENTITY_NAME, NUMBER_PROP);

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.BASIC_PROPERTY_DETAIL));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);

        assertTrue(output.contains("header:"));
        assertTrue(output.contains("Score"));
        assertTrue(output.contains("numberfield"));
    }
}
