#pragma version(1)
#pragma rs java_package_name(com.apps.alexs7.pointop)

rs_allocation inPixels;
int height;
int width;
int threeBythreeTemplatePixels[9];

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {

  uchar4 pixel = in.rgba;

  if(x==0 || x==width || y==0 || y==height){
      pixel.rb = 0;
      pixel.g = 255;
  }else{ //do image processing here
      //get the 3x3 nearby pixels
      int index = 0;
      for(int i=0; i<3; i++){
        for(int k=0; k<3; k++){
            uchar4 currentTemplatePixel = rsGetElementAt_uchar4(inPixels, x+(k-1), y+(i-1));
            int averageValue = (currentTemplatePixel.r + currentTemplatePixel.g + currentTemplatePixel.b)/3;
            threeBythreeTemplatePixels[index] = averageValue;
            index++;
        }
      }
      //index should be the number of the 3x3 template pixels that is 9
      int sum;
      for(int i=0; i<index; i++){
        sum = sum + threeBythreeTemplatePixels[i];
      }

      int finalAvg = sum/index;
      pixel.rgb = finalAvg;
  };

  return pixel;
}