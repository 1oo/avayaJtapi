package com.avaya.jtapi.tsapi.impl.events.termConn;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;

public final class LucentV5TermConnHeldEventImpl extends
		LucentTermConnHeldEventImpl implements LucentV5CallInfo {
	public LucentV5TermConnHeldEventImpl(TermConnEventParams params) {
		super(params);
	}
}