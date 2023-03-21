package com.hoddmimes.transform;

import org.bson.Document;

public interface MessageMongoInterface
{
    public Document getMongoDocument();
    public void decodeMongoDocument( Document pDoc );
}
