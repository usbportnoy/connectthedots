package com.portjm1221.connectthedots.core;

import com.portjm1221.connectthedots.core.models.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorOperation implements GameOperation{
    Logger logger = LoggerFactory.getLogger(ErrorOperation.class);

    private String error;

    public ErrorOperation(String error) {
        this.error = error;
    }

    @Override
    public Payload execute() {
        logger.error(error);
        return null;
    }
}
