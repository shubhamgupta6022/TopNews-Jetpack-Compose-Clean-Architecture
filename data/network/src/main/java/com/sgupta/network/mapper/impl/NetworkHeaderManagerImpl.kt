package com.sgupta.network.mapper.impl

import com.sgupta.network.client.NetworkHost
import com.sgupta.network.header.HeaderHelper
import com.sgupta.network.header.HeaderMap
import com.sgupta.network.mapper.NetworkHeaderManager
import javax.inject.Inject

class NetworkHeaderManagerImpl @Inject constructor(
    private val headerHelper: HeaderHelper
): NetworkHeaderManager {
    override fun convert(from: NetworkHost): HeaderMap {
        return headerHelper.getCommonHeaders()
    }
}