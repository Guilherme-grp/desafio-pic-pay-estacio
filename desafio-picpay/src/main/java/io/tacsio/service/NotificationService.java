package io.tacsio.service;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.tacsio.service.dto.NotificationResponse;

@ApplicationScoped
@RegisterRestClient(configKey = "notification")
public interface NotificationService {

	@GET
	NotificationResponse sendNotification();
}
