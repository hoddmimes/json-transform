package com.hoddmimes.jsontransform;

import org.bson.Document;

public interface MessageMongoInterface
{
    public Document getMongoDocument();
    public void decodeMongoDocument( Document pDoc );
}
