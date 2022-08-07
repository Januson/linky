package linky.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

public final class ApiError {

	private final HttpStatus status;
	private final String message;
	private final List<String> details;
	private final LocalDateTime occurredAt;

	private ApiError(final HttpStatus status, final String message, final LocalDateTime occurredAt,
			final List<String> details) {
		this.status = status;
		this.message = message;
		this.occurredAt = occurredAt;
		this.details = details;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getDetails() {
		return details;
	}

	public LocalDateTime getOccurredAt() {
		return occurredAt;
	}

	public static final class Builder {
		private HttpStatus status;
		private String message;
		private List<String> details = new ArrayList<>();
		private LocalDateTime timeStamp;

		public Builder(final HttpStatus status, final String message) {
			this.status = status;
			this.message = message;
		}

		public Builder withDetail(final String detail) {
			this.details.add(detail);
			return this;
		}

		public Builder withDetails(final List<String> details) {
			this.details = new ArrayList<>(details);
			return this;
		}

		public Builder atTime(final LocalDateTime timeStamp) {
			this.timeStamp = timeStamp;
			return this;
		}

		public ApiError build() {
			return new ApiError(this.status, this.message, this.timeStamp, this.details);
		}
	}
}
