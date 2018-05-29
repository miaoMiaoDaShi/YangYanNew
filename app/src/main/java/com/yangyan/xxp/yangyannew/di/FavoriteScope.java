package com.yangyan.xxp.yangyannew.di;

import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;

@Scope
@Documented
@Retention(AnnotationRetention.RUNTIME)
public @interface FavoriteScope {}