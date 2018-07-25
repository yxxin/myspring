package com.yx.myspring.core.io.support;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yx.myspring.core.io.Resource;
import com.yx.myspring.util.Assert;
import com.yx.myspring.util.ClassUtils;

public class PackageResourceLoader {

	private final ClassLoader classLoader;
	private static final Log logger=LogFactory.getLog(PackageResourceLoader.class);
	public PackageResourceLoader() {
		this.classLoader=ClassUtils.getDefaultClassLoader();
	}
	public PackageResourceLoader(ClassLoader classLoader) {
		Assert.notNull(classLoader, "ResourceLoader must be not null");
		this.classLoader=classLoader;
	}
	
	public Resource[] getResources(String basePackage) {
		Assert.notNull(basePackage, "basePackage  must not be null");
		String resourcePath=ClassUtils.convertClassNameToResourcePath(basePackage);
		URL url=this.classLoader.getResource(resourcePath);
		File rootDir=new File(url.getFile());
		
		Set<File> matchingFiles=retrieveMatchingFiles(rootDir);
		Resource[] result=new Resource[matchingFiles.size()];
		int i=0;
		for(File file:matchingFiles) {
			result[i++]=new FileSystemResource(file);
		}
		return result;
	}
	private Set<File> retrieveMatchingFiles(File rootDir){
		if(!rootDir.exists()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exist");
			}
			return Collections.emptySet();
		}
		if(!rootDir.isDirectory()) {
			if (logger.isWarnEnabled()) {
				logger.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory");
			}
			return Collections.emptySet();
		}
		if(!rootDir.canRead()) {
			if (logger.isWarnEnabled()) {
				logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath()
						+ "] because the application is not allowed to read the directory");
			}
		}
		Set<File> result=new LinkedHashSet<File>(8);
		doRetrieveMatchingFiles(rootDir,result);
		return result;
	}
	private void doRetrieveMatchingFiles(File rootDir,Set<File> result) {
		File[] files=rootDir.listFiles();
		if (files == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("Could not retrieve contents of directory [" + rootDir.getAbsolutePath() + "]");
			}
			return;
		}
		for(File file:files) {
			if(file.isDirectory()) {
				if(!file.canRead()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Skipping subdirectory [" + rootDir.getAbsolutePath()
								+ "] because the application is not allowed to read the directory");
					}
				}else {
					doRetrieveMatchingFiles(file,result);
				}
			}else {
				result.add(file);
			}
		}
	}
	
}
