/**
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.angularjs;

import static org.jboss.forge.addon.angularjs.AngularScaffoldProvider.SCAFFOLD_DIR;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.WebResourcesFacet;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.scaffold.util.ScaffoldUtil;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.addon.templates.facets.TemplateFacet;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;

/**
 * A {@link ProcessingStrategy} to process the contents of the {@link ScaffoldResource} from it's source on the
 * classpath or the project file system with the Freemarker Template engine. The produced output is then written to the
 * destination in the project's web resources.
 */
public class ProcessTemplateStrategy implements ProcessingStrategy
{

   private Logger logger = Logger.getLogger(ProcessTemplateStrategy.class);

   private final WebResourcesFacet web;

   private final ResourceFactory resourceFactory;

   private final Project project;

   private final TemplateFactory templateFactory;

   private final Map<String, Object> dataModel;

   public ProcessTemplateStrategy(WebResourcesFacet web, ResourceFactory resourceFactory, Project project,
            TemplateFactory templateFactory, Map<String, Object> dataModel)
   {
      this.web = web;
      this.resourceFactory = resourceFactory;
      this.project = project;
      this.templateFactory = templateFactory;
      this.dataModel = dataModel;
   }

   @Override
   public Resource<?> execute(ScaffoldResource scaffoldResource)
   {
      logger.debug("generating scaffold for resource [" + scaffoldResource.getSource() + "], and destination [" + scaffoldResource.getDestination() + "], using model [" + dataModel + "]");
      Resource<?> resource = resourceFactory.create(getClass().getResource(
               SCAFFOLD_DIR + scaffoldResource.getSource()));
      if (project.hasFacet(TemplateFacet.class))
      {
         TemplateFacet templates = project.getFacet(TemplateFacet.class);
         Resource<?> templateResource = templates.getResource(scaffoldResource.getSource());
         if (templateResource.exists())
         {
            resource = templateResource;
         }
      }

      Template template = templateFactory.create(resource, FreemarkerTemplate.class);
      String output = null;
      try
      {
         output = template.process(dataModel);
         logger.debug("generated scaffold for resource [" + scaffoldResource.getSource() + "]");
      }
      catch (IOException ioEx)
      {
         logger.error(ioEx);
         throw new IllegalStateException(ioEx);
      }
      return ScaffoldUtil.createOrOverwrite(web.getWebResource(scaffoldResource.getDestination()), output);
   }
}
