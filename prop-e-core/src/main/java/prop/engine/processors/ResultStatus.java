package prop.engine.processors;

public enum ResultStatus {

	SUCCESS,			// was done as planned
	PARTIAL_SUCCESS,	// was performed with what had to be done
	WAS_GONE,			// had already been done

	UNRESOLVED,			// resolution unknown

	ABORTED,			// crashed
	FAILED,				// can't be done
	;
	
}
