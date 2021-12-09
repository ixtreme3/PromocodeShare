/* tslint:disable */
/* eslint-disable */

export interface HttpClient<O> {
  request<R>(requestConfig: {
    method: string;
    url: string;
    queryParams?: any;
    data?: any;
    copyFn?: (data: R) => R;
    options?: O;
  }): RestResponse<R>;
}

export class RestApplicationClient<O> {
  constructor(protected httpClient: HttpClient<O>) {}

  /**
   * HTTP GET /
   * Java method: ru.nsu.promocodesharebackend.controller.HelloWorldController.helloWorld
   */
  helloWorld(options?: O): RestResponse<string> {
    return this.httpClient.request({ method: 'GET', url: uriEncoding``, options: options });
  }

  /**
   * HTTP POST /coupon
   * Java method: ru.nsu.promocodesharebackend.controller.CouponController.create
   */
  create(couponDTO: CouponDTO, options?: O): RestResponse<Coupon> {
    return this.httpClient.request({ method: 'POST', url: uriEncoding`coupon`, data: couponDTO, options: options });
  }

  /**
   * HTTP GET /coupon
   * Java method: ru.nsu.promocodesharebackend.controller.CouponController.findAllPaged
   */
  findAllPaged(queryParams: { page: number; rowsPerPage: number }, options?: O): RestResponse<Coupon[]> {
    return this.httpClient.request({
      method: 'GET',
      url: uriEncoding`coupon`,
      queryParams: queryParams,
      options: options,
    });
  }
}

export interface Coupon {
  code: string;
  description: string;
  expirationDate: DateAsNumber;
  id: number;
  name: string;
  shop: Shop;
  user: User;
}

export interface CouponDTO {
  code: string;
  description: string;
  expirationDate: DateAsNumber;
  isArchive: boolean;
  isDeleted: boolean;
  name: string;
  shopId: number;
  userId: number;
}

export interface Shop {
  category: string;
  href: string;
  id: number;
  name: string;
  title: string;
}

export interface User {
  id: number;
  name: string;
  vkLink: string;
}

export type DateAsNumber = number;

export type RestResponse<R> = Promise<Axios.GenericAxiosResponse<R>>;

function uriEncoding(template: TemplateStringsArray, ...substitutions: any[]): string {
  let result = '';
  for (let i = 0; i < substitutions.length; i++) {
    result += template[i];
    result += encodeURIComponent(substitutions[i]);
  }
  result += template[template.length - 1];
  return result;
}

// Added by 'AxiosClientExtension' extension

import axios from 'axios';
import * as Axios from 'axios';

declare module 'axios' {
  export interface GenericAxiosResponse<R> extends Axios.AxiosResponse {
    data: R;
  }
}

class AxiosHttpClient implements HttpClient<Axios.AxiosRequestConfig> {
  constructor(private axios: Axios.AxiosInstance) {}

  request<R>(requestConfig: {
    method: string;
    url: string;
    queryParams?: any;
    data?: any;
    copyFn?: (data: R) => R;
    options?: Axios.AxiosRequestConfig;
  }): RestResponse<R> {
    function assign(target: any, source?: any) {
      if (source != undefined) {
        for (const key in source) {
          if (source.hasOwnProperty(key)) {
            target[key] = source[key];
          }
        }
      }
      return target;
    }

    const config: Axios.AxiosRequestConfig = {};
    config.method = requestConfig.method as typeof config.method; // `string` in axios 0.16.0, `Method` in axios 0.19.0
    config.url = requestConfig.url;
    config.params = requestConfig.queryParams;
    config.data = requestConfig.data;
    assign(config, requestConfig.options);
    const copyFn = requestConfig.copyFn;

    const axiosResponse = this.axios.request(config);
    return axiosResponse.then((axiosResponse) => {
      if (copyFn && axiosResponse.data) {
        (axiosResponse as any).originalData = axiosResponse.data;
        axiosResponse.data = copyFn(axiosResponse.data);
      }
      return axiosResponse;
    });
  }
}

export class AxiosRestApplicationClient extends RestApplicationClient<Axios.AxiosRequestConfig> {
  constructor(baseURL: string, axiosInstance: Axios.AxiosInstance = axios.create()) {
    axiosInstance.defaults.baseURL = baseURL;
    super(new AxiosHttpClient(axiosInstance));
  }
}
