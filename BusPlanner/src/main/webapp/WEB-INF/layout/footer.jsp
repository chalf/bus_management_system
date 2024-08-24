<%-- 
    Document   : footer
    Created on : Aug 15, 2024, 4:35:01 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://unpkg.com/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://unpkg.com/bs-brain@2.0.4/utilities/font-size/font-size.css">
<link rel="stylesheet" href="https://unpkg.com/bs-brain@2.0.4/utilities/padding/padding.css">


<!-- Footer -->
<footer class="footer">

  <!-- Widgets Section -->
  <section class="bg-light py-4 py-md-5 py-xl-8 border-top border-light">
    <div class="container overflow-hidden">
      <div class="row gy-4 gy-lg-0 justify-content-xl-between">
        
        <!-- Contact Information -->
        <div class="col-12 col-md-4 col-lg-3 col-xl-2">
          <div class="widget">
            <h4 class="widget-title mb-4">Get in Touch</h4>
            <address class="mb-4">8014 Edith Blvd NE, Albuquerque, New York, United States</address>
            <p class="mb-1">
              <a class="link-secondary text-decoration-none" href="tel:+15057922430">(505) 792-2430</a>
            </p>
            <p class="mb-0">
              <a class="link-secondary text-decoration-none" href="mailto:demo@yourdomain.com">demo@yourdomain.com</a>
            </p>
          </div>
        </div>

        <!-- Learn More Links -->
        <div class="col-12 col-md-4 col-lg-3 col-xl-2">
          <div class="widget">
            <h4 class="widget-title mb-4">Learn More</h4>
            <ul class="list-unstyled">
              <li class="mb-2">
                <a href="#!" class="link-secondary text-decoration-none">About</a>
              </li>
              <li class="mb-2">
                <a href="#!" class="link-secondary text-decoration-none">Contact</a>
              </li>
              <li class="mb-2">
                <a href="#!" class="link-secondary text-decoration-none">Advertise</a>
              </li>
              <li class="mb-2">
                <a href="#!" class="link-secondary text-decoration-none">Terms of Service</a>
              </li>
              <li class="mb-0">
                <a href="#!" class="link-secondary text-decoration-none">Privacy Policy</a>
              </li>
            </ul>
          </div>
        </div>

        <!-- Newsletter Subscription -->
        <div class="col-12 col-lg-3 col-xl-4">
          <div class="widget">
            <h4 class="widget-title mb-4">Our Newsletter</h4>
            <p class="mb-4">Subscribe to our newsletter to get our news & discounts delivered to you.</p>
            <form action="#!">
              <div class="row gy-4">
                <div class="col-12">
                  <div class="input-group">
                    <span class="input-group-text" id="email-newsletter-addon">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
                        <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z" />
                      </svg>
                    </span>
                    <input type="email" class="form-control" id="email-newsletter" placeholder="Email Address" aria-label="email-newsletter" aria-describedby="email-newsletter-addon" required>
                  </div>
                </div>
                <div class="col-12">
                  <div class="d-grid">
                    <button class="btn btn-primary" type="submit">Subscribe</button>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>

      </div>
    </div>
  </section>

  <!-- Copyright Section -->
  <div class="bg-light py-4 py-md-5 py-xl-8 border-top border-light-subtle">
    <div class="container overflow-hidden">
      <div class="row gy-4 gy-md-0 align-items-md-center">
        <div class="col-xs-12 col-md-7 order-1 order-md-0">
          <div class="copyright text-center text-md-start">
            &copy; 2024. All Rights Reserved.
          </div>
        </div>

        <!-- Social Media Links -->
        <div class="col-xs-12 col-md-5 order-0 order-md-1">
          <div class="social-media-wrapper">
            <ul class="list-unstyled m-0 p-0 d-flex justify-content-center justify-content-md-end">
              <li class="me-3">
                <a href="#!" class="link-dark link-opacity-75-hover">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-facebook" viewBox="0 0 16 16">
                    <path d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951z" />
                  </svg>
                </a>
              </li>
              <li class="me-3">
                <a href="#!" class="link-dark link-opacity-75-hover">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-youtube" viewBox="0 0 16 16">
                    <path d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.007 2.007 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.007 2.007 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31.4 31.4 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.007 2.007 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A99.788 99.788 0 0 1 7.91 2h.14l.002-.001zM6.4 5.846v4.471l3.578-2.229L6.4 5.846z" />
                  </svg>
                </a>
              </li>
              <li class="me-3">
                <a href="#!" class="link-dark link-opacity-75-hover">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-twitter" viewBox="0 0 16 16">
                    <path d="M5.026 15c6.038 0 9.341-5.003 9.341-9.334 0-.14 0-.282-.007-.423A6.68 6.68 0 0 0 16 3.542a6.658 6.658 0 0 1-1.889.518 3.301 3.301 0 0 0 1.447-1.817 6.533 6.533 0 0 1-2.084.797A3.28 3.28 0 0 0 7.875 7.03 9.325 9.325 0 0 1 1.112 2.1a3.284 3.284 0 0 0 1.015 4.382 3.266 3.266 0 0 1-1.487-.409v.041c0 1.532 1.09 2.809 2.534 3.099a3.285 3.285 0 0 1-1.482.056 3.283 3.283 0 0 0 3.066 2.279A6.587 6.587 0 0 1 .78 13.58a9.313 9.313 0 0 0 5.046 1.475" />
                  </svg>
                </a>
              </li>
              <li class="me-3">
                <a href="#!" class="link-dark link-opacity-75-hover">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-instagram" viewBox="0 0 16 16">
                    <path d="M8 3.3c-2.592 0-2.917.009-3.943.057-1.014.047-1.583.212-1.954.364a3.978 3.978 0 0 0-1.43.932 3.978 3.978 0 0 0-.932 1.43c-.152.371-.317.94-.364 1.954C.91 8.303.9 8.628.9 11.2s.009 2.917.057 3.943c.047 1.014.212 1.583.364 1.954a3.978 3.978 0 0 0 .932 1.43 3.978 3.978 0 0 0 1.43.932c.371.152.94.317 1.954.364 1.026.048 1.351.057 3.943.057s2.917-.009 3.943-.057c1.014-.047 1.583-.212 1.954-.364a3.978 3.978 0 0 0 1.43-.932 3.978 3.978 0 0 0 .932-1.43c.152-.371.317-.94.364-1.954.048-1.026.057-1.351.057-3.943s-.009-2.917-.057-3.943c-.047-1.014-.212-1.583-.364-1.954a3.978 3.978 0 0 0-.932-1.43 3.978 3.978 0 0 0-1.43-.932c-.371-.152-.94-.317-1.954-.364C10.917 3.309 10.592 3.3 8 3.3zM8 0c2.662 0 2.987.01 4.035.058 1.046.048 1.768.217 2.41.464a5.978 5.978 0 0 1 2.158 1.413 5.978 5.978 0 0 1 1.413 2.158c.247.642.416 1.364.464 2.41.048 1.048.058 1.373.058 4.035s-.01 2.987-.058 4.035c-.048 1.046-.217 1.768-.464 2.41a5.978 5.978 0 0 1-1.413 2.158 5.978 5.978 0 0 1-2.158 1.413c-.642.247-1.364.416-2.41.464-1.048.048-1.373.058-4.035.058s-2.987-.01-4.035-.058c-1.046-.048-1.768-.217-2.41-.464a5.978 5.978 0 0 1-2.158-1.413 5.978 5.978 0 0 1-1.413-2.158c-.247-.642-.416-1.364-.464-2.41C.01 10.987 0 10.662 0 8s.01-2.987.058-4.035c.048-1.046.217-1.768.464-2.41A5.978 5.978 0 0 1 1.935.484a5.978 5.978 0 0 1 2.158-1.413c.642-.247 1.364-.416 2.41-.464C5.013.01 5.338 0 8 0zm0 3.889a4.11 4.11 0 1 0 0 8.222 4.11 4.11 0 0 0 0-8.222zm0 6.778a2.669 2.669 0 1 1 0-5.337 2.669 2.669 0 0 1 0 5.337zm4.271-6.778a.986.986 0 1 1 0-1.971.986.986 0 0 1 0 1.971z" />
                  </svg>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</footer>