package com.besaba.revonline.eventbusapt;

import com.google.common.eventbus.Subscribe;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;


@SupportedAnnotationTypes("com.google.common.eventbus.Subscribe")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EventBusAPTProcessor extends AbstractProcessor {
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (final Element element : roundEnv.getElementsAnnotatedWith(Subscribe.class)) {
      if (element.getKind() != ElementKind.METHOD) {
        // can't happen
        continue;
      }

      final ExecutableElement method = ((ExecutableElement) element);

      final int countParameters = method.getParameters().size();
      if (countParameters != 1) {
        processingEnv.getMessager().printMessage(
            Diagnostic.Kind.ERROR,
            String.format("Found %d parameter(s) instead of 1", countParameters),
            method
        );
      }
    }
    return true;
  }
}
