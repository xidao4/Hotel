<template>
    <a-upload
            name="avatar"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            :before-upload="beforeUpload"
            :customRequest="upload"
    >
        <img v-if="imageUrl" :src="imageUrl" alt="avatar" />
        <div v-else>
            <a-icon :type="loading ? 'loading' : 'plus'" />
            <div class="ant-upload-text">
                Upload
            </div>
        </div>
    </a-upload>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
	/*function getBase64(img, callback) {
		const reader = new FileReader();
		reader.addEventListener('load', () => callback(reader.result));
		reader.readAsDataURL(img);
	}*/
	export default {
		data() {
			return {
				loading: false,
			};
		},
		props: {
			avatar_url: String
		},
		mounted() {
			this.imageUrl = this.avatar_url
		},
        computed: {
		    ...mapGetters([
		        'imageUrl'
            ])
        },
		methods: {
		    ...mapMutations([
		        'set_imagaUrl'
            ]),
		    ...mapActions([
		        'getImageUrl'
            ]),
		    async upload(info){
		        const formData = new FormData()
		        formData.append('img',info.file)
                console.log(info)
                await this.getImageUrl(formData)
                this.$emit('editAvatar',this.imageUrl)
                console.log(this.imageUrl)
            },
			handleChange(info) {
				if (info.file.status === 'uploading') {
					this.loading = true;
					return;
				}
				if (info.file.status === 'done') {
					// Get this url from response in real world.
					/*getBase64(info.file.originFileObj, imageUrl => {
						this.imageUrl = imageUrl;
						this.loading = false;
					});*/

					this.$emit('editAvatar',this.imageUrl)
                    console.log(this.imageUrl)
                    console.log("compo_succ")
				}
			},
			beforeUpload(file) {
				const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
				if (!isJpgOrPng) {
					this.$message.error('You can only upload JPG file!');
				}
				const isLt2M = file.size / 1024 / 1024 < 2;
				if (!isLt2M) {
					this.$message.error('Image must smaller than 2MB!');
				}
				return isJpgOrPng && isLt2M;
			},
		},
	};
</script>
<style>
    .avatar-uploader > .ant-upload {
        width: 128px;
        height: 128px;
    }
    .ant-upload-select-picture-card i {
        font-size: 32px;
        color: #999;
    }

    .ant-upload-select-picture-card .ant-upload-text {
        margin-top: 8px;
        color: #666;
    }
</style>
