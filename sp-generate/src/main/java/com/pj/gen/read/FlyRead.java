package com.pj.gen.read;

import com.pj.gen.cfg.GenCfg;

/**
 * 读取的接口
 * @author kongyongshun
 *
 */
public interface FlyRead {

	public FlyRead setCodeCfg(GenCfg codeCfg);
	
	/**
	 * 根据CodeCfg配置读取
	 */
	public void readInfo();
	

	
	
	
}
