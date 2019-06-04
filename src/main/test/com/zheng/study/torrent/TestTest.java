package com.zheng.study.torrent;

import java.util.List;

import lombok.Data;

public class TestTest {
	@Data
	public class Files {
		private long length;
		private String md5sum;
		private List<String> path;

		public Files() {
		}

		public Files(long length, String md5sum, List<String> path) {
			super();
			this.length = length;
			this.md5sum = md5sum;
			this.path = path;
		}

	}

	@Data
	public class Info {
		private String name;
		private byte[] pieces;
		private long piecesLength;
		private long length;
		private String md5sum;
		private List<Files> files;
		public Info() {
		}
	}
	
}
