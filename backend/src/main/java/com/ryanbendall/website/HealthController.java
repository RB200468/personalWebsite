package com.ryanbendall.website;

import com.ryanbendall.website.api.HealthApi;
import com.ryanbendall.website.api.model.Health;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController implements HealthApi {

    @Override
    public ResponseEntity<Health> getHealth() {
        return ResponseEntity.ok(new Health(Health.StatusEnum.UP));
    }
}
