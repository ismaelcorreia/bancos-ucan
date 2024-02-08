
<template>
    <div class="card flex flex-wrap gap-3 p-fluid">
        <div class="flex-auto">
            <label for="buttondisplay" class="font-bold block mb-2"> Button </label>
            <Calendar v-model="buttondisplay" showIcon :showOnFocus="false" inputId="buttondisplay" />
        </div>
        <div class="flex-auto">
            <label for="icondisplay" class="font-bold block mb-2"> Default Icon </label>
            <Calendar v-model="icondisplay" showIcon iconDisplay="input" inputId="icondisplay" />
        </div>
        <div class="flex-auto">
            <label for="templatedisplay" class="font-bold block mb-2"> Custom Icon </label>
            <Calendar v-model="templatedisplay" showIcon iconDisplay="input" timeOnly inputId="templatedisplay">
                <template #inputicon="{ clickCallback }">
                    <i class="pi pi-clock cursor-pointer" @click="clickCallback" />
                </template>
            </Calendar>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";

const buttondisplay = ref();
const icondisplay = ref();
const templatedisplay = ref();
</script>
