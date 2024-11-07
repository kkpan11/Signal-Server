/*
 * Copyright 2021-2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.whispersystems.textsecuregcm.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public sealed interface SubscriptionLevelConfiguration permits
    SubscriptionLevelConfiguration.Backup, SubscriptionLevelConfiguration.Donation {

  Map<String, SubscriptionPriceConfiguration> prices();

  enum Type {
    DONATION,
    BACKUP
  }

  default Type type() {
    return switch (this) {
      case Backup b -> Type.BACKUP;
      case Donation d -> Type.DONATION;
    };
  }

  record Backup(
      @JsonProperty("playProductId") @NotEmpty String playProductId,
      @JsonProperty("mediaTtl") @NotNull Duration mediaTtl,
      @JsonProperty("prices") @Valid Map<@NotEmpty String, @NotNull @Valid SubscriptionPriceConfiguration> prices)
      implements SubscriptionLevelConfiguration {}

  record Donation(
      @JsonProperty("badge") @NotEmpty String badge,
      @JsonProperty("prices") @Valid Map<@NotEmpty String, @NotNull @Valid SubscriptionPriceConfiguration> prices)
      implements SubscriptionLevelConfiguration {}
}
