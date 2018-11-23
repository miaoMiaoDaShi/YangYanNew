package com.yangyan.xxp.yangyannew.di.scope;

import java.lang.annotation.Documented;

import javax.inject.Scope;

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;

@Scope
@Documented
@Retention(AnnotationRetention.RUNTIME)
/**
 * 提供  项目中单例
 */
public @interface YangYanScope {}